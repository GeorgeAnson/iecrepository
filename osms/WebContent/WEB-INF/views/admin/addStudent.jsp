<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
                    <div class="control-group">
                        <label class="control-label" for="inputPassword4"><span class="red">*</span>学生类型</label>
                        <div class="controls">
                          <select class='select2 input-block-level' style="width: 216px;" id="studentType" name="studentType">
                          </select>
                        </div>
                      </div>
                      <div class="control-group">
                        <label class="control-label" for="inputPassword4"><span class="red typer bmer">*</span>担保人</label>
                        <div class="controls">
                          <select class='select2 input-block-level' name="guaranteeId" style="width: 216px;" id="offer">
                            <c:forEach items="${guarantees}" var="guarantee">
                          </select>
                        </div>
                      </div>
                      <div class="control-group">

                        <div class="controls">
                        <div class="controls">
                        <div class="controls">
  </body>