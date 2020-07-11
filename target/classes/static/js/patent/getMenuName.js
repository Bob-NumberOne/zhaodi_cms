/*  Title：获取人力资源浏览按钮
* 1、引入Layui JS和CSS,并放在改文档之前
* 2、添加隐藏元素input: ID===wb_gw 存放所选择的岗位
*/
/*预定HTML:
    <div class="layui-inline">
        <label class="layui-form-label">菜单名称</label>
        <input type="text" id="wb_menu" name="menuId" style="display: none">
        <input style="width: 190px" type="text" id="menuBrowse" name="menuName" lay-verify="title" autocomplete="off" placeholder="请选择菜单" class="layui-input">
    </div>
    <div id="menu_applicant" lay-filter="menu_applicant" style="display: none;padding-left: 30px;padding-top: 30px;">
        <div id="menu_choose" class="demo-transfer"></div>
    </div>
*/
layui.use(['transfer'],function() {
    var transfer = layui.transfer
        ,layer = layui.layer;
    var menu_data= [];
    function getMenuName(){
        $.ajax({
            async: false,
            url: 'basic/getMenuName.do',
            data: {},
            dataType: 'json',
            type: 'post',
            success: function (data) {
                if(data.data.length>0){
                    menu_data=data.data;
                }
            }
        });
        //显示搜索框
        transfer.render({
            elem: '#menu_choose'
            ,data: menu_data
            ,title: ['菜单', '选择结果']
            ,showSearch: true
            ,id: 'menuName_key' //定义唯一索引
        });

    }
    // 为输入框绑定光标聚焦事件的触发该函数，
    $('#menuBrowse').focus(menu_input_focus_handler);

    // “客户”输入框的光标聚焦事件的触发函数， 弹出弹层，弹层上显示所有的客户，以供选择。
    function menu_input_focus_handler() {
        if(menu_data==null || menu_data.length<=0){
            getMenuName();
        }
        layer.open({
            type : 1
            ,title: ['用户名', 'font-size:18px;']
            , area : [ '550px', '530px' ]
            , content : $('#menu_applicant')
            ,btn: ['确认', '取消']
            , success : function () {
                // 重新加载表格中的数据
                $('#menu_applicant').css('display', 'block');
            },yes: function(index, layero){//确定操作
                var getData = transfer.getData('menuName_key'); //获取右侧数据
                //alert(getData[0].title);
                if(getData.length>1){
                    layer.msg("只能选择一个菜单！");
                }else{
                    $("#menuBrowse").val(getData[0].title);
                    $("#wb_menu").val(getData[0].value);
                    layer.close(index); //如果设定了yes回调，需进行手工关闭
                }

            }
        });
    }
})
