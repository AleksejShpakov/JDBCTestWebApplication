<%@ page import="entities.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JDBCTestWebApplication</title>
  <%@ include file="/views/main/defaultResources.jsp" %>
</head>
<body>
<%@ include file="/views/main/header.jsp" %>

<div class="container" style="padding-top: 15px; margin-left: 15px; margin-right: 15px;">

    <div class="row">

        <div class="col-xs-4 col-sm-4 col-md-4">

            <div class="row form-group">
                <div class="col-xs-4 col-sm-4 col-md-4">
                    <label>Имя*</label>
                </div>
                <div class="col-xs-8 col-sm-8 col-md-8">
                    <input id="input_user_name" type="text" name="name" maxlength="32" required="required">
                </div>
            </div>

            <div class="row form-group">
                <div class="col-xs-4 col-sm-4 col-md-4">
                    <label>Фамилия*</label>
                </div>
                <div class="col-xs-8 col-sm-8 col-md-8">
                    <input id="input_user_surname" type="text" name="surname" maxlength="32">
                </div>
            </div>

            <div class="row form-group">
                <div class="col-xs-4 col-sm-4 col-md-4">
                    <label>Отчество*</label>
                </div>
                <div class="col-xs-8 col-sm-8 col-md-8">
                    <input id="input_user_patronymic" type="text" name="patronymic" maxlength="32">
                </div>
            </div>

            <div class="row form-group">
                <div class="col-md-4 offset-md-8">
                    <button class="btn btn-success float-right" onclick="addUser();">Добавить</button>
                </div>
            </div>
        </div>

        <div class="col-xs-8 col-sm-8 col-md-8">
          <table class="table" id="users_table">
            <tr>
              <th>№</th>
              <th>Имя</th>
              <th>Фамилия</th>
              <th>Отчество</th>
            </tr>

          <%ArrayList<User> userList = (ArrayList<User>) request.getAttribute("userList");
            for(int i = 0; i < userList.size(); i++){%>
            <tr>
              <td><%=i%></td>
              <td><%=userList.get(i).getName()%></td>
              <td><%=userList.get(i).getSurName()%></td>
              <td><%=userList.get(i).getPatronymic()%></td>
            </tr>
          <%}%>

          </table>
        </div>

    </div>

</div>

<%@ include file="/views/main/footer.jsp" %>


<script>
    function addUser() {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/AddUser",
            data: {
                name: document.getElementById('input_user_name').value,
                surname: document.getElementById('input_user_surname').value,
                patronymic: document.getElementById('input_user_patronymic').value
            },
            success: function(msg){
                alert( "Прибыли данные: " + msg );
            }
        });
    };
</script>

</body>
</html>

