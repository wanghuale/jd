<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="文章内容"%>
<%@ attribute name="length" type="java.lang.String" required="true" description="保留长度"%>
<c:choose>
<c:when test="${fn:length(value) > length}">
<span title='${fns:htmlEscape(value)}'>${fn:substring(fns:htmlEscape(value),0,length)}...</span>
</c:when>
<c:otherwise>
${value}
</c:otherwise>
</c:choose>
