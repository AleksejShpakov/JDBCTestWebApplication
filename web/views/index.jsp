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

<div class="container-fluid" style="padding-top: 15px;">

    <div class="row">

        <div class="col-xs-3 col-sm-3 col-md-3">
            <div class="card">
                <h4 class="card-header">Добавление пользователя</h4>

                <div class="card-body">
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

                    <div class="row">
                        <div class="col-md-4 offset-md-8">
                            <button class="btn btn-success float-right" onclick="addUser();">Добавить</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card" style="margin-top: 15px;">
                <h4 class="card-header">Удаление пользователя</h4>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4 offset-md-8">
                            <button id='remove_user_btn' class="btn btn-danger float-right" onclick="removeUser();" disabled>Удалить</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="table_area" class="col-xs-6 col-sm-6 col-md-6" style="padding-bottom: 15px;">
            <div class="card">
                <h4 class="card-header">Таблица пользователей</h4>
                <div class="card-body">
                    <table class="table table-hover" id="users_table">
                        <thead>
                            <tr>
                                <th>№</th>
                                <th>Имя</th>
                                <th>Фамилия</th>
                                <th>Отчество</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-xs-3 col-sm-3 col-md-3">
            <div class="card">
                <h4 class="card-header">Поиск пользователей</h4>
                <div class="card-body">

                    <div class="row form-group">
                        <div class="col-xs-12 col-sm-12 col-md-12">
                            <div class="input-group">
                                <select id="user_search_type" class="form-control input-group-addon">
                                    <option value="name">Имя</option>
                                    <option value="surname">Фамилия</option>
                                    <option value="patronymic">Отчество</option>
                                </select>
                                <input class="form-control" id="input_user_search_parameter" type="text" name="name" maxlength="32" required="required">
                            </div>
                        </div>

                    </div>

                    <div class="row">
                        <div class="col-md-4 offset-md-8">
                            <button id='search_user_btn' class="btn btn-success float-right" onclick="searchUsers();">Найти</button>
                        </div>
                    </div>

                </div>
            </div>

            <div class="card" style="margin-top: 15px;">
                <h4 class="card-header">Показать всех пользователей</h4>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-4 offset-md-8">
                            <button id='show_all_users_btn' class="btn btn-success float-right" onclick="refreshTable('users_table');">Показать</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<%@ include file="/views/main/footer.jsp" %>


<script>

    $('#users_table').bootstrapTable({
        clickToSelect: true,
        singleSelect: true,
        columns: [{
            checkbox:true
        },{
            field: 'index',
            title: '№'
        }, {
            field: 'name',
            title: 'Имя'
        }, {
            field: 'surname',
            title: 'Фамилия'
        }, {
            field: 'patronymic',
            title: 'Отчество'
        } ],
        onCheck: function () {
            var quantityOfSelections;
            quantityOfSelections = $('#users_table').bootstrapTable('getSelections').length;
            $('#remove_user_btn').prop('disabled',false);
        },
        onUncheck: function () {
            var quantityOfSelections;
            quantityOfSelections = $('#users_table').bootstrapTable('getSelections').length;
            if (quantityOfSelections == 0) {
                $('#remove_user_btn').prop('disabled', true);
            }
        }
    });

    refreshTable('users_table');

    function addUser() {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/UserServlet",
            data: {
                method: 'addUser',
                name: document.getElementById('input_user_name').value,
                surname: document.getElementById('input_user_surname').value,
                patronymic: document.getElementById('input_user_patronymic').value
            },
            success: function(data){
                if (data.status == 'ERROR') {
                    alert(data.message);
                    return;
                }
                refreshTable('users_table');
            }
        });
    };

    function removeUser() {
        var selectedRow;
        selectedRow = $('#users_table').bootstrapTable('getSelections')[0];
        $('#remove_user_btn').prop('disabled', true);

        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/UserServlet",
            data: {
                method: 'removeUser',
                name: selectedRow.name,
                surname: selectedRow.surname,
                patronymic: selectedRow.patronymic
            },
            success: function(data){
                if (data.status == 'ERROR') {
                    alert(data.message);
                    return;
                }
                refreshTable('users_table');
            }
        });
    };

    function searchUsers() {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/UserServlet",
            data: {
                method: 'searchUsers',
                search_parameter_type: document.getElementById('user_search_type').value,
                search_parameter_value: document.getElementById('input_user_search_parameter').value
            },
            success: function(data){
                var users;
                if (data.status == 'ERROR') {
                    alert(data.message);
                    return;
                }
                users = data.users;
                for (var i = 0; i < users.length; i++){
                    users[i].index = i;
                }

                $('#users_table').bootstrapTable('load', users);
            }
        });
    };

    function refreshTable(table_id) {
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/UserServlet",
            data: {
                method: 'getAllUsers',
                name: document.getElementById('input_user_name').value,
                surname: document.getElementById('input_user_surname').value,
                patronymic: document.getElementById('input_user_patronymic').value
            },
            success: function(data){
                var users;

                if (data.status == 'ERROR') {
                    alert.log(data.message);
                    return;
                }

                users = data.users;
                for (var i = 0; i < users.length; i++){
                    users[i].index = i;
                }

                $('#users_table').bootstrapTable('load', users);
            }
        });
    };
</script>

</body>
</html>

