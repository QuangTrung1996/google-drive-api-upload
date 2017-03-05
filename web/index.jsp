<%--
  Created by IntelliJ IDEA.
  User: schrodinger
  Date: 3/4/2017
  Time: 9:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <form id="uploadForm" action="upload" method="post">
      <input type="file" name="file">
      <input type="submit">
    </form>
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.form.min.js"></script>
    <script>
      $(document).ready(function () {
        function processUploadResult(data) {
            alert(data.result);
        }
        $('#uploadForm').submit(function(event) {
          $(this).ajaxSubmit({
              success : processUploadResult(),
              url : '${pageContext.request.contextPath}/upload',
              type : 'post',
              dataType : 'json'
          });
          event.preventDefault();
        });
      })
    </script>
  </body>
</html>
