<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*,java.sql.*,java.io.*,javax.servlet.*,javax.servlet.http.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
    request.setCharacterEncoding("utf-8");
    response.setCharacterEncoding("utf-8");
//******************************卓正PageOffice组件的使用*******************************
    PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
//poCtrl1.setServerPage(request.getContextPath() + "/poserver.zz"); //此行必须
    poCtrl1.setServerPage("poserver.do"); //此行必须
    poCtrl1.setSaveFilePage("savefile.jsp");//执行保存操作的文件或者请求路径


//1:保存 2：盖章 3：批注文本 4：全屏  5:打钩 6：打印  7：搜索  0：文本列表
    poCtrl1.addCustomToolButton("保存", "Save", 1);
    poCtrl1.addCustomToolButton("普通编辑", "docNormalEdit", 3);
    poCtrl1.addCustomToolButton("痕迹编辑", "docRevisionOnly", 3);
    poCtrl1.addCustomToolButton("显示痕迹", "ShowRevisions", 5);
    poCtrl1.addCustomToolButton("隐藏痕迹", "HiddenRevisions", 5);
    poCtrl1.addCustomToolButton("手写批注", "StartHandDraw", 3);


    String path=request.getSession().getServletContext().getRealPath("/") ;
//String path=getClass().getResource("/").getFile().toString();

    String fullpath = "";//完整路径
    String filepath = request.getParameter("fileId") == null ? "":request.getParameter("fileId");//文件存放路径
    poCtrl1.setCaption("在线编辑  "+filepath);
//如果是文件存放路径，要加上服务器地址，组合成完整路径
    if (filepath != null && !"".equals(filepath)
            && !"null".equals(filepath)
            && !"undefined".equals(filepath)) {
        fullpath = path +"\\"+filepath;
    }

    filepath = "F:\\github\\tb\\tb-usermanage\\a.docx";
    fullpath = filepath;
//获取文件类型
    String fileType=filepath.substring(filepath.lastIndexOf("."));
    if(fileType.trim().equals(".doc")||fileType.trim().equals(".docx")) {
        poCtrl1.webOpen(fullpath, OpenModeType.docNormalEdit, "aa");//可编辑模式打开word文档
//poCtrl1.webOpen(fullpath, OpenModeType.docReadOnly, "aa");//只读模式打开word文档
    } else if(fileType.trim().equals(".xls")||fileType.trim().equals(".xlsx")) {
        poCtrl1.webOpen(fullpath, OpenModeType.xlsNormalEdit, "aa");//可编辑模式打开excel文档
//poCtrl1.webOpen(fullpath, OpenModeType.xlsReadOnly, "aa");//只读模式打开excel文档
    } else if(fileType.trim().equals(".ppt")||fileType.trim().equals(".pptx")) {
        poCtrl1.webOpen(fullpath, OpenModeType.pptNormalEdit, "aa");//可编辑模式打开ppt文档
//poCtrl1.webOpen(fullpath, OpenModeType.pptReadOnly, "aa");//只读模式打开ppt文档
    }

    poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须要！
%>




<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <link href="images/csstg.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        //流程跳转
        function Lz(id,flag) {
            if(flag==0){
                location.href=encodeURI("index.jsp?id="+id+"&flg=李四批阅");
            }
            else
                location.href=encodeURI("index.jsp?id="+id+ "&flg=文员清稿");
        }
    </script>
    <style>
        * {
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<form id="form2">
    <div id="content">
        <div id="textcontent" style="width:auto; height:100%;">
            <!--**************   卓正 PageOffice组件 ************************-->
            <script type="text/javascript">
                function Save() {
                    //判断是否为附件
                    var fullpath = "<%=fullpath%>";
                    if(fullpath.indexOf('http:') == 0){
                        if(window.confirm("该文件不是附件不能保存到服务器！\n        是否另存为本地文件?")){
                            document.getElementById("PageOfficeCtrl1").ShowDialog( 3 );
                        }else{

                        }
                        return;
                    }
                    //提示是否保存服务器
                    if (window.confirm("是否要将修改后的内容保存到服务器?")) {
                        document.getElementById("PageOfficeCtrl1").WebSave();
                    } else {

                    }
                }





                //显示痕迹
                function ShowRevisions() {
                    document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
                }




                //隐藏痕迹
                function HiddenRevisions() {
                    document.getElementById("PageOfficeCtrl1").ShowRevisions = false;
                }

                //领导圈阅签字
                function StartHandDraw() {
                    document.getElementById("PageOfficeCtrl1").HandDraw.SetPenWidth(5);
                    document.getElementById("PageOfficeCtrl1").HandDraw.Start();
                }
                // 插入键盘批注
                function StartRemark() {
                    var appObj = document.getElementById("PageOfficeCtrl1").WordInsertComment();




                }
                //分层显示手写批注
                function ShowHandDrawDispBar() {
                    document.getElementById("PageOfficeCtrl1").HandDraw.ShowLayerBar(); ;
                }




                //全屏/还原
                function IsFullScreen() {
                    alert("全屏/还原");
                    document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
                }




                //显示标题
                function ShowTitle() {
                    alert("该菜单的标题是：" + document.getElementById("PageOfficeCtrl1").caption);
                }

                //痕迹编辑模式
                function docRevisionOnly(){
                    var fullpath = '<%=fullpath%>';
                    document.getElementById("PageOfficeCtrl1").Close();
                    document.getElementById("PageOfficeCtrl1").WebOpen( fullpath, "docRevisionOnly", "张三" );
                }

                //普通编辑模式
                function docNormalEdit(){
                    var fullpath = '<%=fullpath%>';
                    alert(fullpath);
                    document.getElementById("PageOfficeCtrl1").Close();
                    document.getElementById("PageOfficeCtrl1").WebOpen("系统管理概要设计.doc", "docNormalEdit", "张三");
                }
            </script>
            <po:PageOfficeCtrl id="PageOfficeCtrl1" />
        </div>
    </div>
</form>
</body>
</html>