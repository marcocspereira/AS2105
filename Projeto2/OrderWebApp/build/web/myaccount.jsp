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
            function handleChange(input, limit) {
                if (input.value < 0)
                    input.value = 0;
                if (input.value > limit)
                    input.value = limit;
            }
            
            function addToCart(to_buy, code_product, uprice){
                var result = parseInt(to_buy)*parseInt(uprice);
                $('#text1').val('cona');
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
            <textarea name="Text1" id="text1"></textarea>
            
            <s:form id="ordersForm" name="order" action="OrderAction">
                <h3><span class="glyphicon glyphicon-tree-conifer glyphicon-align-left" aria-hidden="true"></span> Trees</h3>
                <table>
                    <tr>
                        <td class="header_td">ID</td>
                        <td class="header_td">Description</td>
                        <td class="header_td">Quantity</td>
                        <td class="header_td">Price/Unity</td>
                        <td class="header_td">Add</td>
                    </tr>
                    <c:forEach var="arvore" items="${bean.trees}" varStatus="myIndex">
                        <tr>
                            <td><c:out value="${arvore.product_code}" /></td>
                            <td><c:out value="${arvore.description}" /></td>
                            <td><c:out value="${arvore.quantity}" /></td>
                            <td><c:out value="${arvore.price}" /></td>
                            <td>
                                <input type="number" class="input_text" onchange="handleChange(this,${arvore.quantity});" onkeyup="handleChange(this,${arvore.quantity});" onblur="addToCart(this,${arvore.product_code},${arvore.price});" />                                
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <h3><span class="glyphicon glyphicon-grain glyphicon-align-left" aria-hidden="true"></span> Seeds</h3>
                <table>
                    <tr>
                        <td class="header_td">ID</td>
                        <td class="header_td">Description</td>
                        <td class="header_td">Quantity</td>
                        <td class="header_td">Price/Unity</td>
                        <td class="header_td">Add</td>
                    </tr>
                    <c:forEach var="arvore" items="${bean.seeds}" varStatus="myIndex">
                        <tr>
                            <td><c:out value="${arvore.product_code}" /></td>
                            <td> <c:out value="${arvore.description}" /></td>
                            <td><c:out value="${arvore.quantity}" /></td>
                            <td><c:out value="${arvore.price}" /></td>
                            <td>
                                <input type="number" class="input_text" onchange="handleChange(this,${arvore.quantity});" onkeyup="handleChange(this,${arvore.quantity});" />                                
                            </td>                               
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="5" class="addToChart"><input type="button" value="Add to Cart!"></td>
                    </tr>
                </table>

                <h3><span class="glyphicon glyphicon-certificate glyphicon-align-left" aria-hidden="true"></span> Shrubs</h3>
                <table>
                    <tr>
                        <td class="header_td">ID</td>
                        <td class="header_td">Description</td>
                        <td class="header_td">Quantity</td>
                        <td class="header_td">Price/Unity</td>
                        <td class="header_td">Add</td>
                    </tr>
                    <c:forEach var="arvore" items="${bean.shrubs}" varStatus="myIndex">
                        <tr>
                            <td><c:out value="${arvore.product_code}" /></td>
                            <td> <c:out value="${arvore.description}" /></td>
                            <td><c:out value="${arvore.quantity}" /></td>
                            <td><c:out value="${arvore.price}" /></td>
                            <td>
                                <input type="number" class="input_text" onchange="handleChange(this,${arvore.quantity});" onkeyup="handleChange(this,${arvore.quantity});" />                                
                            </td>                              
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="5" class="addToChart"><input type="button" value="Add to Cart!"></td>
                    </tr>
                </table>
            </s:form>

        </div>

    </body>
</html>
