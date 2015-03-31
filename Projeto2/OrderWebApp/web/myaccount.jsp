<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%--<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0">
        <meta name="description" content="Projeto de AS!">
        <meta name="author" content="Marco Pereira">
        <title><s:text name="title.myaccount"/></title>

        <!-- Bootstrap-->
        <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="bootstrap/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="bootstrap/css/style.css">

        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>

        <script type="text/javascript">
            function copyToCart(celula) {
                // window.alert(celula);
                celula = celula.trim();
                var content = celula + ";\n";
                $('#text1').append(content);
            }

        </script>

    </head>
    <sb:head/>

    <body>

        <s:if test="#session.login != 'true'">
            <jsp:forward page="index.jsp" />
        </s:if>

        <nav class="navbar navbar-default navbar-static-top"  role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">EPE</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-menu" id="bs-example-navbar-collapse-1">
                    <ul class="nav navbar-nav navbar-right">
                        <li style="margin: 12px 5px;"><c:out value="${bean.loginUser}"> </c:out></li>
                            <li>
                            <s:form id="logoutForm" name="logout" action="LogoutAction">
                                <s:submit cssClass="btn btn-default btn-danger" key="btn.logout" method="execute" align="center"/>
                            </s:form>
                        </li>
                    </ul>
                </div><!-- /.navbar-collapse -->
            </div><!-- /.container-fluid -->
        </nav>

        <div class="contentor_div">
            <br>

            <h3><span class=" glyphicon glyphicon-shopping-cart glyphicon-align-left" aria-hidden="true"></span> Cart</h3>    
        <s:form id="ordersForm" name="order" action="OrderAction" cssClass="formToSubmit">
                <s:textfield id="textfield" name="bean.orderFirstName" label="Primeiro Nome" placeholder="Primeiro nome do cliente"/>
                <s:textfield id="textfield" name="bean.orderLastName" label="Último Nome" placeholder="Último nome do cliente"/>
                <s:textfield id="textfield" name="bean.orderAddress" label="Morada" placeholder="Morada do cliente"/>
                <s:textfield id="textfield" name="bean.orderPhoneNumber" label="Telefone" placeholder="Telefone do cliente"/>

                <s:textarea name="Text1" id="text1" cssClass="text1" disabled="true" />

                <s:submit cssClass="btn btn-medium btn-primary" key="btn.order" method="execute"/>
            </s:form>

            <br />

            <div role="tabpanel">

                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active">
                        <a href="#trees" aria-controls="trees" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-tree-conifer glyphicon-align-left" aria-hidden="true"></span>
                            Trees
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="#seeds" aria-controls="seeds" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-grain glyphicon-align-left" aria-hidden="true"></span> Seeds
                            Seeds
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="#shrubs" aria-controls="shrubs" role="tab" data-toggle="tab">
                            <span class="glyphicon glyphicon-certificate glyphicon-align-left" aria-hidden="true"></span>
                            Shrubs
                        </a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="trees">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <tr>
                                    <td><b>Código | Descrição | Quantidade | Preço</b></td>
                                </tr>
                                <c:forEach var="arvore" items="${bean.trees}" varStatus="myIndex">
                                    <tr>
                                        <td onclick="copyToCart(this.innerHTML)">
                                            <c:out value="${arvore.product_code}" /> | <c:out value="${arvore.description}" /> | <c:out value="${arvore.quantity}" /> | <c:out value="${arvore.price}" />                                            
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="seeds">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <tr>
                                    <td><b>Código | Descrição | Quantidade | Preço</b></td>
                                </tr>
                                <c:forEach var="arvore" items="${bean.seeds}" varStatus="myIndex">
                                    <tr>
                                        <td onclick="copyToCart(this.innerHTML)">
                                            <c:out value="${arvore.product_code}" /> | <c:out value="${arvore.description}" /> | <c:out value="${arvore.quantity}" /> | <c:out value="${arvore.price}" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>

                    <div role="tabpanel" class="tab-pane" id="shrubs">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <tr>
                                    <td><b>Código | Descrição | Quantidade | Preço</b></td>
                                </tr>
                                <c:forEach var="arvore" items="${bean.shrubs}" varStatus="myIndex">
                                    <tr>
                                        <td onclick="copyToCart(this.innerHTML)">
                                            <c:out value="${arvore.product_code}" /> | <c:out value="${arvore.description}" /> | <c:out value="${arvore.quantity}" /> | <c:out value="${arvore.price}" />
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </div>
                </div>

            </div>


        </div>

    </body>
</html>
