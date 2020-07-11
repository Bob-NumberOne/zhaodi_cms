/*  Title：获取人员岗位浏览按钮
* 1、引入Layui JS和CSS,并放在改文档之前
* 2、添加隐藏元素input: ID===wb_gw 存放所选择的岗位
*/
/*预定HTML:
    <div class="layui-inline">
        <label class="layui-form-label">岗位</label>
        <input type="text" id="wb_gw" name="jobtitlename" style="display: none">
        <input style="width: 190px" type="text" id="JobTitle" name="JobTitle" lay-verify="title" autocomplete="off" placeholder="请选择岗位" class="layui-input">
    </div>
    <div id="JobTitle_pplicant" lay-filter="JobTitle_pplicant" style="display: none;padding-left: 30px;padding-top: 30px;">
        <div id="JobTitle_choose" class="demo-transfer"></div>
    </div>
*/
layui.use(['transfer'],function() {
    var transfer = layui.transfer
        ,layer = layui.layer;
    var JobTitle_data= [];
    function getHrmresource(){
        $.ajax({
            async: false,
            url: 'project/getJobTitle.xmj',
            data: {},
            dataType: 'json',
            type: 'post',
            success: function (data) {
                JobTitle_data=data.data;
            }
        });
        //显示搜索框
        transfer.render({
            elem: '#JobTitle_choose'
            ,data: JobTitle_data
            ,title: ['岗位', '选择结果']
            ,showSearch: true
            ,id: 'JobTitle_key' //定义唯一索引
        });

    }
    // 为输入框绑定光标聚焦事件的触发该函数，
    $('#JobTitle').focus(applicant_input_focus_handler);

    // “客户”输入框的光标聚焦事件的触发函数， 弹出弹层，弹层上显示所有的客户，以供选择。
    function applicant_input_focus_handler() {
        if(JobTitle_data==null || JobTitle_data.length<=0){
            getHrmresource();
        }
        layer.open({
            type : 1
            ,title: ['岗位', 'font-size:18px;']
            , area : [ '550px', '530px' ]
            , content : $('#JobTitle_pplicant')
            ,btn: ['确认', '取消']
            , success : function () {
                // 重新加载表格中的数据
                $('#JobTitle_pplicant').css('display', 'block');
            },yes: function(index, layero){//确定操作
                //do something
                var getData = transfer.getData('JobTitle_key'); //获取右侧数据
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
                $("#JobTitle").val(textName);
                $("#wb_gw").val(text);
                layer.close(index); //如果设定了yes回调，需进行手工关闭
            }
        });
    }
});
