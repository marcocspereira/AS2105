<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags"%>
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
    </head>
    <sb:head/>
    
    <body>
        
        <div>
            <b>Name:</b><c:out value="${bean.loginUser}"></c:out>
            <div role="tabpanel">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Trees</a></li>
    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Seeds</a></li>
    <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Shrubs</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="home">...</div>
    <div role="tabpanel" class="tab-pane" id="profile">...</div>
    <div role="tabpanel" class="tab-pane" id="messages">...</div>
  </div>

</div>
        </div>
        <div>
            <table>
                
            </table>
        </div>
        
        
        <h1>Hello World!</h1>
    </body>
</html>
