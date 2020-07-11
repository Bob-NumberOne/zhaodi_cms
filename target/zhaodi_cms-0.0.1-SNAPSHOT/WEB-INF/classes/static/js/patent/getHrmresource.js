/*  Title：获取人力资源浏览按钮
* 1、引入Layui JS和CSS,并放在改文档之前
* 2、添加隐藏元素input: ID===wb_gw 存放所选择的岗位
*/
/*预定HTML:
    <div class="layui-inline">
        <label class="layui-form-label">用户名</label>
        <input type="text" id="wb_user" name="userName" style="display: none">
        <input style="width: 190px" type="text" id="userBrowse" lay-verify="title" autocomplete="off" placeholder="请选择人员" class="layui-input">
    </div>
    <div id="user_applicant" lay-filter="user_applicant" style="display: none;padding-left: 30px;padding-top: 30px;">
        <div id="user_choose" class="demo-transfer"></div>
    </div>
*/
layui.use(['transfer'],function() {
    var transfer = layui.transfer
        ,layer = layui.layer;
    var user_data= [];
    function getHrmresource(){
        $.ajax({
            async: false,
            url: 'hrm/getHrmResourcerowse.do',
            data: {},
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if(data.data.length>0){
                    user_data=data.data;
                }
            }
        });
        //显示搜索框
        transfer.render({
            elem: '#user_choose'
            ,data: user_data
            ,title: ['姓名', '选择结果']
            ,showSearch: true
            ,id: 'userName_key' //定义唯一索引
        });

    }
    // 为输入框绑定光标聚焦事件的触发该函数，
    $('#userBrowse').focus(applicant_input_focus_handler);

    // “客户”输入框的光标聚焦事件的触发函数， 弹出弹层，弹层上显示所有的客户，以供选择。
    function applicant_input_focus_handler() {
        if(user_data==null || user_data.length<=0){
            getHrmresource();
        }
        layer.open({
            type : 1
            ,title: ['用户名', 'font-size:18px;']
            , area : [ '550px', '530px' ]
            , content : $('#user_applicant')
            ,btn: ['确认', '取消']
            , success : function () {
                // 重新加载表格中的数据
                $('#user_applicant').css('display', 'block');
            },yes: function(index, layero){//确定操作
                var getData = transfer.getData('userName_key'); //获取右侧数据
                //alert(getData[0].title);
                var text="";
                var textName="";
                for(var i=0;i<getData.length;i++){
                    if(i==getData.length-1){
                        text+=getData[i].value;
                        textName+=getData[i].title;
                    }else {
                        text+=getData[i].value+",";
                        textName+=getData[i].title+",";
                    }
                }
                $("#userBrowse").val(textName);
                $("#wb_user").val(text);
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        });
    }
})
