package com.whty.platform.modules.hongcheng.utils;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.jpos.space.Space;
import org.jpos.space.SpaceFactory;
import org.jpos.util.Loggeable;

public class FSDMsg implements Loggeable, Cloneable {
	public static char FS = '\034';
	public static char US = '\037';
	public static char GS = '\035';
	public static char RS = '\036';
	public static char EOF = '\000';
	public static char PIPE = '\u007C';
	public static char EOM = '\000';

	private static final Set<String> DUMMY_SEPARATORS = new HashSet<String>(
			Arrays.asList("DS", "EOM"));
	private static final String EOM_SEPARATOR = "EOM";

	Map fields;
	Map separators;

	String baseSchema;
	String basePath;
	byte[] header;

	/**
	 * Creates a FSDMsg with a specific base path for the message format schema.
	 * 
	 * @param basePath
	 *            schema path, for example: "file:src/data/NDC-" looks for a
	 *            file src/data/NDC-base.xml
	 */
	public FSDMsg(String basePath) {
		this(basePath, "base");
		/**/

	}

	/**
	 * Creates a FSDMsg with a specific base path for the message format schema,
	 * and a base schema name. For instance, FSDMsg("file:src/data/NDC-",
	 * "root") will look for a file: src/data/NDC-root.xml
	 * 
	 * @param basePath
	 *            schema path
	 * @param baseSchema
	 *            schema name
	 */
	public FSDMsg(String basePath, String baseSchema) {
		super();
		fields = new LinkedHashMap();
		separators = new LinkedHashMap();
		this.basePath = basePath;
		this.baseSchema = baseSchema;

		setSeparator("FS", FS);
		setSeparator("US", US);
		setSeparator("GS", GS);
		setSeparator("RS", RS);
		setSeparator("EOF", EOF);
		setSeparator("PIPE", PIPE);
	}

	public String getBasePath() {
		return basePath;
	}

	public String getBaseSchema() {
		return baseSchema;
	}

	/*
	 * add a new or override an existing separator type/char pair.
	 * 
	 * @param separatorName string of type used in definition (FS, US etc)
	 * 
	 * @param separator char representing type
	 */
	public void setSeparator(String separatorName, char separator) {
		separators.put(separatorName, Character.valueOf(separator));
	}

	/*
	 * add a new or override an existing separator type/char pair.
	 * 
	 * @param separatorName string of type used in definition (FS, US etc)
	 * 
	 * @param separator char representing type
	 */
	public void unsetSeparator(String separatorName) {
		if (separators.containsKey(separatorName)) {
			separators.remove(separatorName);
		} else {
			throw new RuntimeException("unsetSeparator was attempted for "
					+ separatorName + " which was not previously defined.");
		}
	}

	/**
	 * parse message. If the stream ends before the message is completely read,
	 * then the method adds an EOF field.
	 * 
	 * @param is
	 *            input stream
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 * @throws MalformedURLException
	 */
	public void unpack(InputStream is) throws IOException, JDOMException,
			MalformedURLException {
		try {
			unpack(is, getSchema(baseSchema));
		} catch (EOFException e) {
			fields.put("EOF", "true");
		}
	}

	/**
	 * parse message. If the stream ends before the message is completely read,
	 * then the method adds an EOF field.
	 * 
	 * @param b
	 *            message image
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 * @throws MalformedURLException
	 * @throws ISOException
	 */
	public void unpack(byte[] b) throws IOException, JDOMException,
			MalformedURLException {
		unpack(new ByteArrayInputStream(b));
	}

	/**
	 * @return message string
	 * @throws ISOException
	 */
	public String pack() throws JDOMException, MalformedURLException,
			IOException, ISOException {
		StringBuffer sb = new StringBuffer();
		pack(getSchema(baseSchema), sb);
		return sb.toString();
	}

	public byte[] packToBytes() throws JDOMException, MalformedURLException,
			IOException, ISOException, UnsupportedEncodingException {
		return pack().getBytes(ISOUtil.ENCODING);
	}

	protected String get(String id, String type, int length, String defValue,
			String separator) throws ISOException {
		String value = (String) fields.get(id);
		if (value == null)
			value = defValue == null ? "" : defValue;

		type = type.toUpperCase();

		switch (type.charAt(0)) {
		case 'N': // number
			if (isSeparated(separator)) {
				// Leave value unpadded.
			} else {
				value = ISOUtil.zeropad(value, length);
			}
			break;
		case 'A': // asc
			if (isSeparated(separator)) {
				// Leave value unpadded.
			} else {
				value = ISOUtil.strpad(value, length);
			}
			if (value.length() > length)
				value = value.substring(0, length);

			value = StringUtil.asciiToHex(value);
			break;
		case 'K':
			if (defValue != null)
				value = defValue;
			break;
		case 'B': // bcd
			try {
				if ((length << 1) >= value.length()) {
					if (isSeparated(separator)) {
						// Convert but do not pad if this field ends with a
						// separator
						value = new String(ISOUtil.hex2byte(value),
								ISOUtil.ENCODING);
					} else {
						value = new String(ISOUtil.hex2byte(ISOUtil.zeropad(
								value, length << 1).substring(0, length << 1)),
								ISOUtil.ENCODING);
					}
				} else {
					throw new RuntimeException("field content=" + value
							+ " is too long to fit in field " + id
							+ " whose length is " + length);
				}
			} catch (UnsupportedEncodingException ignored) {
				// should not happen
			}
			break;
		}
		return (isSeparated(separator)) && !EOM_SEPARATOR.equals(separator) ? ISOUtil
				.blankUnPad(value)
				: value;
	}

	private boolean isSeparated(String separator) {
		/*
		 * if type's last two characters appear in our Map of separators, return
		 * true
		 */
		if (separator != null) {
			if (separators.containsKey(separator)) {
				return true;
			} else {
				if (isDummySeparator(separator)) {
					return true;
				}

				throw new RuntimeException(
						"FSDMsg.isSeparated(String) found that " + separator
								+ " has not been defined as a separator!");
			}
		}
		return false;

	}

	private boolean isDummySeparator(String separator) {
		return DUMMY_SEPARATORS.contains(separator);
	}

	private boolean isBinary(String type) {
		/*
		 * if type's first digit is a 'B' return true
		 */
		return type.startsWith("B");
	}

	public boolean isSeparator(byte b) {
		return separators.containsValue(Character.valueOf((char) b));
	}

	private String getSeparatorType(String type) {
		if (type.length() > 2) {
			return type.substring(1);
		}
		return null;
	}

	private char getSeparator(String separator) {
		if (separators.containsKey(separator)) {
			return ((Character) separators.get(separator)).charValue();
		} else {
			if (isDummySeparator(separator)) {
				// Dummy separator type, return 0 to indicate nothing to add.
				return 0;
			}
		}

		throw new RuntimeException("getSeparator called on separator="
				+ separator + " which does not resolve to a known separator.");
	}

	protected void pack(Element schema, StringBuffer sb) throws JDOMException,
			MalformedURLException, IOException, ISOException {
		String keyOff = "";
		String defaultKey = "";
		Iterator iter = schema.getChildren("field").iterator();
		while (iter.hasNext()) {
			Element elem = (Element) iter.next();
			String id = elem.getAttributeValue("id");
			int length = Integer.parseInt(elem.getAttributeValue("length"));
			String type = elem.getAttributeValue("type");
			// For backward compatibility, look for a separator at the end of
			// the type attribute, if no separator has been defined.
			String separator = elem.getAttributeValue("separator");
			if (type != null && separator == null) {
				separator = getSeparatorType(type);
			}
			boolean key = "true".equals(elem.getAttributeValue("key"));
			Map properties = key ? loadProperties(elem) : Collections.EMPTY_MAP;
			String defValue = elem.getText();
			// If properties were specified, then the defValue contains lots of
			// \n and \t in it. It should just be set to the empty string, or
			// null.
			if (!properties.isEmpty()) {
				defValue = defValue.replace("\n", "").replace("\t", "")
						.replace("\r", "");
			}
			String value = get(id, type, length, defValue, separator);
			sb.append(value);

			if (isSeparated(separator)) {
				char c = getSeparator(separator);
				if (c > 0)
					sb.append(c);
			}
			if (key) {
				String v = isBinary(type) ? ISOUtil.hexString(value
						.getBytes(ISOUtil.ENCODING)) : value;
				keyOff = keyOff + normalizeKeyValue(v, properties);
				defaultKey += elem.getAttributeValue("default-key");
			}
		}
		if (keyOff.length() > 0)
			pack(getSchema(getId(schema), keyOff, defaultKey), sb);
	}

	private Map loadProperties(Element elem) {
		Map props = new HashMap();
		Iterator iter = elem.getChildren("property").iterator();
		while (iter.hasNext()) {
			Element prop = (Element) iter.next();
			String name = prop.getAttributeValue("name");
			String value = prop.getAttributeValue("value");
			props.put(name, value);
		}
		return props;
	}

	private String normalizeKeyValue(String value, Map properties) {
		if (properties.containsKey(value)) {
			return (String) properties.get(value);
		}
		return ISOUtil.normalize(value);
	}

	protected void unpack(InputStream is, Element schema) throws IOException,
			JDOMException, MalformedURLException

	{
		Iterator iter = schema.getChildren("field").iterator();
		String keyOff = "";
		String defaultKey = "";
		while (iter.hasNext()) {
			Element elem = (Element) iter.next();

			String id = elem.getAttributeValue("id");
			int length = Integer.parseInt(elem.getAttributeValue("length"));
			String type = elem.getAttributeValue("type").toUpperCase();
			String separator = elem.getAttributeValue("separator");
			if (type != null && separator == null) {
				separator = getSeparatorType(type);
			}
			boolean key = "true".equals(elem.getAttributeValue("key"));
			Map properties = key ? loadProperties(elem) : Collections.EMPTY_MAP;
			String value = readField(is, id, length, type, separator);

			if (key) {
				keyOff = keyOff + normalizeKeyValue(value, properties);
				defaultKey += elem.getAttributeValue("default-key");
			}
			if ("K".equals(type) && !value.equals(elem.getText()))
				throw new IllegalArgumentException("Field " + id + " value='"
						+ value + "' expected='" + elem.getText() + "'");
		}
		if (keyOff.length() > 0) {
			unpack(is, getSchema(getId(schema), keyOff, defaultKey));
		}
	}

	private String getId(Element e) {
		String s = e.getAttributeValue("id");
		return s == null ? "" : s;
	}

	protected String read(InputStream is, int len, String type, String separator)
			throws IOException {
		StringBuilder sb = new StringBuilder();
		byte[] b = new byte[1];
		boolean expectSeparator = isSeparated(separator);
		boolean separated = expectSeparator;

		if (EOM_SEPARATOR.equals(separator)) {
			// Grab what's left. is.available() should work because it is
			// normally a ByteArrayInputStream
			byte[] rest = new byte[is.available()];
			is.read(rest, 0, rest.length);
			for (int i = 0; i < rest.length; i++) {
				sb.append((char) (rest[i] & 0xff));
			}
		} else if (isDummySeparator(separator)) {
			/*
			 * No need to look for a seperator, that is not there! Try and take
			 * len bytes from the is.
			 */
			for (int i = 0; i < len; i++) {
				if (is.read(b) < 0) {
					break; // end of stream indicates end of field?
				}
				sb.append((char) (b[0] & 0xff));
			}
		} else {

			for (int i = 0; i < len; i++) {
				if (is.read(b) < 0) {
					if (!"EOF".equals(separator))
						throw new EOFException();
					else {
						separated = false;
						break;
					}
				}
				if (expectSeparator && (b[0] == getSeparator(separator))) {
					separated = false;
					break;
				}
				sb.append((char) (b[0] & 0xff));
			}

			if (separated && !"EOF".equals(separator)) {
				if (is.read(b) < 0) {
					throw new EOFException();
				}
			}
		}
		return sb.toString();
	}

	protected String readField(InputStream is, String fieldName, int len,
			String type, String separator) throws IOException {
		String fieldValue = read(is, len, type, separator);

		if (isBinary(type))
			fieldValue = ISOUtil.hexString(fieldValue
					.getBytes(ISOUtil.ENCODING));
		fields.put(fieldName, fieldValue);
		// System.out.println ("++++ "+fieldName + ":" + fieldValue + " " + type
		// + "," + isBinary(type));
		return fieldValue;
	}

	public void set(String name, String value) {
		if (value != null)
			fields.put(name, value);
		else
			fields.remove(name);
	}

	public void setHeader(byte[] h) {
		this.header = h;
	}

	public byte[] getHeader() {
		return header;
	}

	public String getHexHeader() {
		return header != null ? ISOUtil.hexString(header).substring(2) : "";
	}

	public String get(String fieldName) {
		return (String) fields.get(fieldName);
	}

	public String get(String fieldName, String def) {
		String s = (String) fields.get(fieldName);
		return s != null ? s : def;
	}

	public void copy(String fieldName, FSDMsg msg) {
		fields.put(fieldName, msg.get(fieldName));
	}

	public byte[] getHexBytes(String name) {
		String s = get(name);
		return s == null ? null : ISOUtil.hex2byte(s);
	}

	public int getInt(String name) {
		int i = 0;
		try {
			i = Integer.parseInt(get(name));
		} catch (Exception e) {
		}
		return i;
	}

	public int getInt(String name, int def) {
		int i = def;
		try {
			i = Integer.parseInt(get(name));
		} catch (Exception e) {
		}
		return i;
	}

	public Element toXML() {
		Element e = new Element("message");
		if (header != null) {
			e.addContent(new Element("header").setText(getHexHeader()));
		}
		Iterator iter = fields.keySet().iterator();
		while (iter.hasNext()) {
			String fieldName = (String) iter.next();
			Element inner = new Element(fieldName);
			inner.addContent(ISOUtil.normalize((String) fields.get(fieldName)));
			e.addContent(inner);
		}
		return e;
	}

	protected Element getSchema() throws JDOMException, IOException {
		return getSchema(baseSchema);
	}

	protected Element getSchema(String message) throws JDOMException,
			IOException {
		return getSchema(message, "", null);
	}

	protected Element getSchema(String prefix, String suffix, String defSuffix)
			throws JDOMException, IOException {
		StringBuilder sb = new StringBuilder(basePath);
		sb.append(prefix);
		prefix = sb.toString(); // little hack, we'll reuse later with defSuffix
		sb.append(suffix);
		sb.append(".xml");
		String uri = sb.toString();

		Space sp = SpaceFactory.getSpace();
		Element schema = (Element) sp.rdp(uri);
		if (schema == null) {
			SAXBuilder builder = new SAXBuilder();
			URL url = new URL(uri);
			File f = new File(url.getFile());
			if (f.exists()) {
				schema = builder.build(url).getRootElement();
			} else if (defSuffix != null) {
				sb = new StringBuilder(prefix);
				sb.append(defSuffix);
				sb.append(".xml");
				url = new URL(sb.toString());
				f = new File(url.getFile());
				if (f.exists()) {
					schema = builder.build(url).getRootElement();
				}
			}
			if (schema == null) {
				throw new RuntimeException(f.getCanonicalPath() + " not found");
			}
			sp.out(uri, schema);
		}
		return schema;
	}

	/**
	 * @return message's Map
	 */
	public Map getMap() {
		return fields;
	}

	public void setMap(Map fields) {
		this.fields = fields;
	}

	@Override
	public void dump(PrintStream p, String indent) {
		String inner = indent + "  ";
		p.println(indent + "<fsdmsg schema='" + basePath + baseSchema + "'>");
		if (header != null) {
			append(p, "header", getHexHeader(), inner);
		}
		Iterator iter = fields.keySet().iterator();
		while (iter.hasNext()) {
			String f = (String) iter.next();
			String v = ((String) fields.get(f));
			append(p, f, v, inner);
		}
		p.println(indent + "</fsdmsg>");
	}

	private void append(PrintStream p, String f, String v, String indent) {
		p.println(indent + f + ": '" + v + "'");
	}

	public boolean hasField(String fieldName) {
		return fields.containsKey(fieldName);
	}

	@Override
	public Object clone() {
		try {
			FSDMsg m = (FSDMsg) super.clone();
			m.fields = (Map) ((LinkedHashMap) fields).clone();
			return m;
		} catch (CloneNotSupportedException e) {
			throw new InternalError();
		}
	}

	public void merge(FSDMsg m) {
		Iterator<Map.Entry<String, String>> iter = m.fields.entrySet()
				.iterator();
		while (iter.hasNext()) {
			Map.Entry<String, String> entry = iter.next();
			set(entry.getKey(), entry.getValue());
		}
	}
}
