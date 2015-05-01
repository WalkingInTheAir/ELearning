<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="path" uri="/BasePath"%>
   <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
   <%@taglib prefix="p" uri="/Pagination"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<path:basepath/>
</head>
<body>
<ul class="nav nav-tabs" role="tablist">
		<li role="presentation" class="active" id="showFAQTab">
			<a href="#showFAQ" role="tab" data-toggle="tab">FAQ信息</a>
		</li>
		<li role="presentation" id="addFAQTab">
			<a href="#addFAQ" role="tab" data-toggle="tab">添加FAQ</a>
		</li>
		<li role="presentation" id="modifyFAQTab" style="display:none">
			<a href="#modifyFAQ" role="tab" data-toggle="tab">修改FAQ</a>
		</li>
	</ul>

	<div class="tab-content">
		<div role="tabpanel" class="tab-pane active" id="showFAQ">
			<table class="table table-hover data-table"
				style="font-size: 13px; margin-left: 10%; width: 80%;">
				<thead>
					<tr>
						<th>编号</th>
						<th>问题名称</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="faq" items="${requestScope.cata.content }" varStatus="row">
						<tr class="${row.count%2==0?'':'info' }">
							<td>${row.count }</td>
							<td>${faq.name }</td>
							<td>
								<button type="button" class="btn btn-success btn-xs"
									data-id="${faq.id }"
									data-name="${faq.name }"
									data-answer="${faq.answer }"
									onclick="_modifyFAQ(this)">修改</button>
								<button type="button" class="btn btn-danger  btn-xs"
									data-id="${faq.id }"
									onclick="_deleteFAQ(this)">删除</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p:pagination />
		</div>
		<div role="tabpanel" class="tab-pane fade" id="addFAQ">
			<div class="form-group">
				<label for="faqName" class="col-sm-2 control-label">问题名称</label>
				<div class="col-sm-10">
					<input class="form-control" style="width:auto;" id="faqName"></select>
				</div>
			</div>
			<div class="form-group">
				<label for="faqAnswer" class="col-sm-2 control-label">问题答案</label>
				<div class="col-sm-10">
					<textarea class="form-control" style="width:auto;" rows="5" cols="40" id="faqAnswer"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="addFAQBtn" value="添加">
				</div>
			</div>
		</div>
		<div role="tabpanel" class="tab-pane fade" id="modifyFAQ">
			<div class="form-group">
				<label for="faqName" class="col-sm-2 control-label">问题名称</label>
				<div class="col-sm-10">
					<input class="form-control" style="width:auto;" id="faqName"></select>
				</div>
			</div>
			<div class="form-group">
				<label for="faqAnswer" class="col-sm-2 control-label">问题答案</label>
				<div class="col-sm-10">
					<textarea class="form-control" style="width:auto;" rows="5" cols="40" id="faqAnswer"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-5 control-label"></label>
				<div class="col-sm-5">
					<input type="button" class="btn btn-primary" id="mdfFAQBtn" value="修改">
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="page_teacher/js/FAQ.js"></script>
</body>
</html>