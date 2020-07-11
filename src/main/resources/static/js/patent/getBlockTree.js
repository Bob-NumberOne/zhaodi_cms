/*  Title：获取专利树的公共方法
* 1、添加触发元素ID:getTreeBtn
* 2、添加隐藏元素input: ID===belongToBlock 存放所选择树的参数；实现一个方法Tree_Click_Handler()来接收树点击后返回的参数
* 3、需要在body中添加元素：<div style="display: none" id="patentTree" class="demo-tree-more"></div>
* 4、后端加参数处理：
*           List<String> blocks=new ArrayList<>();
*           if (block!=null){
*               blocks=Arrays.asList(block);
*           }
*/
var font_Color="rgb(95, 184, 120)";
layui.use(['tree'],function() {
    var tree = layui.tree
        ,layer = layui.layer;
    function findTreeArr(arr, newArr) {
        if(arr!=null){
            for (let i = 0; i < arr.length; i++) {
                newArr.push(arr[i].id);
                findTreeArr(arr[i].children, newArr);
            }
        }
        return newArr;
    }
    function getBlockTree() {
        var datas={};
        $.ajax({
            async: false,
            url: 'OA/getAtreeMenu.xmj',
            data: {},
            dataType: 'json',
            type: 'GET',
            success: function (data) {
                datas=data
            },
            error:function(request){
                alert("请求失败");
            }
        });
        tree.render({
            elem: '#patentTree'
            ,data: datas
            ,showCheckbox: false  //是否显示复选框
            ,id: 'getBlockTree'
            ,isJump: true //是否允许点击节点时弹出新窗口跳转
            ,onlyIconControl: true  //是否仅允许节点左侧图标控制展开收缩
            ,click: function(obj){
                var data = obj.data;  //获取当前点击的节点数据
                var arr=[];
                arr.push(data.id);
                arr= findTreeArr(data.children,arr);
                $("#belongToBlock").val(arr);
                $("#patentTree .layui-tree-txt").css("color","rgb(85, 85, 85)");
                $("#patentTree div[data-id='"+data.id+"'] .layui-tree-txt").css("color",font_Color);
            }
        });
    }
    $("#getTreeBtn").click(function () {
        //获取数据
        if($("#patentTree").children().length==0){
            getBlockTree();
        }
        //打开一个树的弹框，点击后调用查询方法
        layer.open({
            type: 1
            ,title: ['分块筛选', 'font-size:18px;'] //不显示标题栏
            ,closeBtn: false
            ,area: [ '600px', '700px']
            ,id: 'LAY_layui_patent' //设定一个id，防止重复弹出
            ,maxmin: true
            ,btn: ['确定', '取消']
            ,btnAlign: 'c'
            ,moveType: 1 //拖拽模式，0或者1
            ,content: $('#patentTree')
            ,success: function(index){
                //关闭窗口
                /*layer.close(index); //如果设定了yes回调，需进行手工关闭*/
            }
        });
    })
})