define(["ocxControl","app/requestFlow","easygridEdit","readCard",
        "app/tab_HospitalPrompt","app/tab_prepayGrid","app/tab_userContact","app/tab_treatmentCard","app/tab_baseExamine","app/tab_medicalSurance","app/tab_baseInfo","btnExt","btnSearch"],
    function(ocx,requestFlow,$e,$r,tabHospitalPrompt,tabPrepayGrid,tabUserContact,tabTreatmentCard,tabBaseExamine,tabMedicalSurance,tabBaseInfo){

    /*
    * 重构
    * 1拆分tab 将每个tab做成单独模块
    * 2模块注入器
    * 3模块自身扩展功能
    *
    * 作为集成各个子功能的 顶层模块
    *
    *
    * */

    // requestFlow.prototype.promise = function (obj) {
    //     this.promise = obj;
    // }
   /*
   * ['','m1卡','二代身份证','社保卡','其他卡']
   * */
    var configEnum = {
        cardType: {
            unknowCard: 0,
            m1Card: 1,
            idCard : 2,
            medicareCard : 3,
            ortherCard : 4
        }
    }
    var back = {
        base: "",
        pagePath:"",
        patientId:"",
        searchBtn:null,
        listenReadIDCard : function () {},
        listenReadTreatmentCard : function () {},
        listenBookbuilding : function (){},
	    // initTab : function () { throw new Error("方法未实现,需要执行init初始化") },
        initTab: function(patientInfo){//根据病人信息初始化tab中用户信息
            var me = this;
            $('#tabsA').tabs('select', 0);
            me.userFormLoadData(patientInfo);
            var birthday =  patientInfo.birthday.substring(0,10);
            $("#birthday").val(birthday);
            $T.setBirVal(birthday);
            // $("#age").val($T.getAgeByBir(birthday));
            me.patientId = patientInfo.id;
            me.getAccountBalanceByPid(me.patientId);
            me.hasPatId();
        },
        onPatIdChange: function(){//触发院内提示检查
            var me = this;
            //1 觸發院内提示检查
            $('#grid-l-prompt').datagrid('reload', {patientId: me.patientId});
            $('#grid-l-prompt').datagrid({ onLoadSuccess : function () {
                var  rowsData = $('#grid-l-prompt').datagrid('getRows');
                if(rowsData.length > 0) {
                    $(".icon-exclamation").removeClass("hide");
                }else {
                    $(".icon-exclamation").addClass("hide");
                }
            }
            });
        },
        hasPatId : function(){//是否有patID处理
            var me = this;
            if(!me.patientId) {
                $("#tabsA").find(".btn").attr("disabled","disabled");
            } else {
                $("#tabsA").find(".btn").removeAttr("disabled");
                me.onPatIdChange();
            }
        },
        nameSearchSync : function(){//搜索框和姓名字段输入同步

            // 导致问题
            // $('.txt-nameSearch').keyup(function () {
            //     $('#name').val($.trim($(this).val()));
            // }).blur(function () {
            //     $('#name').val($.trim($(this).val()));
            // });
        },
        // 社保卡读卡方式
        init : function (base,pagePath,socialCardType) {//加载事件
            var me = this;
            var b = back;
            if(base != null)  b.base = base;
            if(pagePath != null)  b.pagePath = pagePath;

            if(pagePath == "inpReg") {
                // 需求 所有按钮 只查询 不建档
                // b.btnReadOnly = true;
                // 需求 要求可以发卡
                b.btnReadOnly = false;
            }
            b.socialCardType =socialCardType;
            // 如果 patientId 为空则显示遮罩

            var specialInfoD = null;
            var specialInfoTypeD = null;

            //var idCardId = null;//用来暂存身份证物理ID
            var idCardInfo = null;//用来暂存身份证号码、姓名、性别等等各种信息
            b.patientId = $('#pid').val();
            // 待注釋
            var tabPtId = {
                tabsA: [b.patientId, '', '', '', '', '', '']
            };
            //
            var kinsfolkAppelArr = null;
            var certTypeArr = null;

            var idCard = null;
            var idCard_Type=null;

            var dict = {};
            var paramDict = {paraType : ['special_info','special_info_type','relation','document_type']};
            window.windowHeight = null;

            me.nameSearchSync();//搜索框和姓名字段输入同步

            // -------------------------------  内部函数   ---------------------------------------------------------------
            function chkPid () {
                // 如果 住院模块 直接 return
                //设置表单输入项是否只读
                formEnabled();
                if(b.btnReadOnly){  return;  }
                // 设置 按钮全部隐藏 和 初始化院内提示！号
                if(!b.patientId) {
                    $("#tabsA").find(".btn").attr("disabled","disabled");

                } else {
                    $("#tabsA").find(".btn").removeAttr("disabled");
                  //  onPatIdChange();
                }

            }
            b.chkPid =chkPid;

            function formEnabled () {

                if(b.pagePath == "cardMgr") return; // 卡界面不设置只读
                if(!b.patientId) {
                    tabBaseInfo.formEditing();
                } else {
                    tabBaseInfo.formReadyOnly();
                }
            }

            // 重载 用户基本信息表单
            function userFormLoadData(data,callback) {
                tabBaseInfo.setFormData(data);
                typeof callback == "function" && callback();
            }

            // 刷新患者信息

            function setMedicalInsure () {
                $ajax.post(b.base + '/ui/outp/patientMedicalInsure/findByPatientId', {patientId: b.patientId}).done(function (rst) {
                    if (rst.code === '200') {
                        try{
                            // rst.data.servantSign = ['是', '否'][rst.data.servantSign];

                            if(rst && rst.data ) {
                                var tmp = {};
                                tmp.insureName = rst.data.insureName;
                                tmp.insureStatusName = rst.data.insureStatusName;
                                tmp.personTypeName = rst.data.personTypeName;
                                for (var a in tmp) {
                                    $("#" + a).val(rst.data[a]);
                                }
                            } else {
                                // 没有查询到患者医保就清空表单

                                $("#insureName").val("");
                                $("#insureStatusName").val("");
                                $("#personTypeName").val("");

                            }


                                    // tabBaseInfo.setFormData(tmp);  // 填入医保信息
                        } catch (e) {
                            console.error(e);
                        }
                        // $('.form-insurance').form('load', rst.data);
                    }
                });
            }


	    // 对外暴露该方法

            function initTab (patientInfo) {
                // tabBaseInfo.reset();  影响后续建档发卡逻辑
                $('#tabsA').tabs('select', 0);
                // // todo 移除
                // debugger;

                userFormLoadData(patientInfo,chkPid);
                setMedicalInsure();
                // var birthday =  patientInfo.birthday.substring(0,10);
                // $("#age").val($T.getAgeByBir(birthday));
                // $("#birthday").val(birthday);
                // end
                // b.patientId = patientInfo.id; // da待检查

                tabHospitalPrompt.refresh(b);
                tabPrepayGrid.refresh(b);
                tabUserContact.refresh(b);
                tabTreatmentCard.refresh(b);
                tabBaseExamine.refresh(b);
                getRrefreshSrcChannel(b.patientId);
              //  getAccountBalanceByPid(b.patientId);
            }
	        b.initTab = initTab;

	        //刷新挂号界面患者来源值   20200827 TODO
	        //加载患者信息时，需要根据患者ID，查询挂号记录最晚的一条挂号信息的患者来源
	        function getRrefreshSrcChannel (pId) {
	        	if($('#srcChannel').length > 0){
	        		$ajax.postSync(base + '/ui/outp/regist/getLatelyRegNumberByPatientId?patientId='+pId).done(function (rst) {
		                if (rst.code === '200') {
		                	if(rst.data){
		                		$('#srcChannel').combobox('setValue',rst.data.srcChannel);
		                	}
		                
		                }
		            });
	        	}
	        }

            // 弹出支付确认框
            function openPaymentLayerOrsetCheck (rst,cb) {
                var userInfo = tabBaseInfo.getFormData();  //$('.form-userInfo').sovals();
                var url = window.location.href;
                if(url.indexOf("ui/outp/patientCard/cardManage") >= 0) {
                    // 1 判断outp016值是否大于0，大于0，则弹出支付界面，
                    if(rst.data.outp016 > 0 ) {
                        var payUrl =b.base +  '/ui/trans/expenses/pay?moduleNumber=9&totalPay='+rst.data.outp016+'&patientId='+ (rst.data.id || userInfo.id)+'&cardNumber='+idCard;

                        $pop.iframePop({
                            title : '支付',
                            content :payUrl,
                            area : ['600px',rst.data.windowHeight+'px'],
                            end : function(){
                                cb();
                            }
                        });
                        return;
                    }
                    // 2 完成支付
                }else {
                    //发新诊疗卡时，将挂号界面的是否收取押金费用复选框选中
                    if(Number($("#chargeDeposit").val())){
                        $("#isChargeDeposit").attr("checked", true);
                        $("#isChargeDeposit2").attr("checked", true);
                    }
                    cb();
                }
            }

            b.openPaymentLayerOrsetCheck = openPaymentLayerOrsetCheck;

            // 查询患者 账户 填入账户余额
            function getAccountBalanceByPid (pid){
                var req = new requestFlow(function () {
                    return $ajax.post(b.base + '/ui/outp/patientAccount/getAccountByPid', {patientId: pid });
                },{
                    onSuccess : function (res) {
                        $("#accountBalance").val(res.data.accountBalance);
                    }
                });
              //  req.send(); //这里一直都没有运行 需要和 产品确认
                // $ajax.post(b.base + '/ui/outp/patientAccount/getAccountByPid', {patientId: pid }).done(function (rst) {
                //     if (rst.code === '200' || rst.code === '201') {
                //         $("#accountBalance").val(rst.data.accountBalance);
                //     }
                // });
            }

            // 初始化请求
            $ajax.postSync(b.base + '/ui/sys/dict/getMoreList',paramDict).done(function (rst) {
                b.dict =  dict = rst.data;
                b.specialInfoD =  specialInfoD = dict.special_info;
                b.specialInfoTypeD =  specialInfoTypeD = dict.special_info_type;
                b.kinsfolkAppelArr =  kinsfolkAppelArr = dict.relation;
                b.certTypeArr =  certTypeArr = dict.document_type;
            });



            // 院内提醒子组件
            tabHospitalPrompt.init("#tab_HospitalPrompt",b);
            // 预交金子组件
            tabPrepayGrid.init("#tab_prepayGrid",b);
            // 联系人组件
            tabUserContact.init("#tab_userContact",b);
            // 就诊卡 在卡管理需要显示

            tabTreatmentCard.init("#treatmentCard",b,{
                columns: [[
                    {title: 'id', field: 'id', hidden: true},
                    {title: '患者ID', field: 'patientId', hidden: true},
                    {title:'操作' ,field:'opt', width:80 ,formatter: function (value,row,index) {
                        //return '<span class="s-op op-lock icon-lock" title="挂失" style="font-size:16px;"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="s-op s-op-run op-unlock icon-unlock_alt" style="font-size:16px;top:1px;" title="解挂"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span class="s-op op-refund icon-credit_card" title="退卡"></span>';
                        //2019-1-02 为了避免出现不可预期的问题，暂时先屏蔽掉诊卡管理中的挂失解挂功能。
                    	return '<span class="s-op op-refund icon-credit_card" title="退卡"></span>';
                    },hidden : b.pagePath !== "cardMgr"}, // ,
                    {title: '类型', field: 'cardTypeName', width: 80},
                    {title: '卡号', field: 'cardNumber', width: 80},
                    {title: '押金', field: 'cardDeposit', width: 80},
                    {title: '状态', field: 'cardStateName', width: 80},
                    {title: 'cardState', field: 'cardState', width: 80, hidden: true},
                    //{title: '发卡机构', field: 'hospName', width: 80},
                    {title: '发卡时间', field: 'issuingDate', width: 140},
                    {title: '发卡人', field: 'issuingOperatorName', width: 80},
                    {title: '备注', field: 'remarks', width: 80}
                ]]
            });
            // 基本体查
            tabBaseExamine.init("#tab_baseExamine",b,{
                columns:[[
                    // {title:'操作',field:'op',width:70, rowspan :2 ,formatter: function (v,row,index) {
                    //     return '<span class="s-op s-op-del icon-del" title="删除"></span>';
                    // }},
                    {title:'记录时间', align :"center",field:'createDate' ,rowspan :2 ,align :'center' ,width:140  },
                    {title:'远视力',  field:'id3', colspan:4, align :'center' },
                    {title:'近视力',  field:'id2', colspan:2, align :'center',width:100},
                    {title:'矫正视力',  field:'id4', colspan:2, align :'center' },
                    {title:'眼压mmhg',field:'id5',  colspan:2,align :'center' },
                    {title:'',field:'id6',  colspan:4,align :'center',hidden:true }
                ],[
                    {title:'左眼',field:'scdOs',width:70 },
                    {title:'说明',field:'scdDescOs',width:100 },
                    {title:'右眼',field:'scdOd',width:70 },
                    {title:'说明',field:'scdDescOd',width:100 },

                    {title:'左眼',field:'scnOs',width:70 },
                    {title:'右眼',field:'scnOd',width:70 },

                    {title:'左眼',field:'ccdOs',width:70},
                    {title:'右眼',field:'ccdOd',width:70},
                    {title:'左眼',field:'iopOs',width:70},
                    {title:'右眼',field:'iopOd',width:70},
                    {title:'id',field:'id',hidden:true},
                    {title:'patientId',field:'patientId',hidden:true},
                    {title:'patientName',field:'patientName',hidden:true},
                    {title:'regNumber',field:'regNumber',hidden:true}
                ]]
            });
            // 社保卡
            tabMedicalSurance.init("#tabMedicalSurance",b);
            // 基本信息 表单
            tabBaseInfo.init("#tab_baseInfo",b);

            // 隐藏按钮
            chkPid();


            $(function () {



                // 建档按钮 封装方案 ---------------------------------------------------------------------------------
                // 用于住院 以及 卡管理使用
                // 点击 请求后逻辑弹窗和执行操作代码封装  .new-pop    getInfoGrid2  相关html

                // 相关函数  onlyBookbuildingDone --》    b.listenBookbuilding(res); //外部注册方法
                //                               --> initTab
                // 弹窗事件注册
                //只建档
                $("#onlyBookbuilding").click(function () {
                    var userInfo =  tabBaseInfo.getFormData(); // $('.form-userInfo').sovals();
                    var formValidate =  tabBaseInfo.formValidate();  // $(".form-userInfo").form('validate');
                    if(!formValidate) return;
                    if(b.patientId) return $pop.alert( userInfo.name + '已存在档案信息无需再建档');
                    $ajax.post({
                        url : b.base+ "/ui/outp/patientCard/qrcodeCardNo",
                        type: "POST",
                        data: userInfo,
                        callback : function (res) {
                            onlyBookbuildingDone(res.data);
                        }
                    });
                });
                //建档按钮事件
                var onlyBookbuildingDone = function (res) {
                    if(res.status == '3') {
                        //患者建档成功 ( 这里不会进入这个条件分支 无卡号)
                        $pop.msg("建档成功！");
                        initTab(res.list);
                        b.listenBookbuilding(res); //外部注册方法
                    } else if(res.status == '2'){
                        //  返回同名患者集合 --> 请选择一个患者  ---》 前台条件 都不是
                        $('#getInfoGrid2').datagrid('loadData', res.list);
                        $('.new-pop').addClass('show-pop');
                    }else if(res.status == '1'){
                        //检索出卡号对应的患者信息，则提示卡号存在，不再后续处理 ( 这里不会进入这个条件分支 无卡号)
                        $pop.err("卡号为："+ res.qrcodeCardNo+"的卡，已存在发卡记录，请更换卡号！");
                    }
                    // bind event
                    ;(function() {
                        // if(bookbuildingDoneInit) return;
                        // bookbuildingDoneInit = true;
                        $('.btn-bookbuildingOldUser').unbind("click");
                        $(".btn-confirmAllNot").unbind("click");
                        $(".btn-bookbuildingcancel").unbind("click");

                        var qrcodeCardNo = $("#qrcodeCardNo").val();

                        // 选中以有用户
                        $(".btn-bookbuildingOldUser").val("确 认");
                        $(".btn-bookbuildingOldUser").click(function () {
                            var userInfo = $('#getInfoGrid2').datagrid('getSelected');
                            if(!userInfo) return   $pop.msg('请选择用户行！');
                            initTab(userInfo);
                            b.listenBookbuilding(userInfo);
                            $('.new-pop').removeClass('show-pop');
                        });
                        // 以上用户全不是，给用户直接发卡
                        $(".btn-confirmAllNot").val("以上都不是,为新患者建档");
                        $(".btn-confirmAllNot").click(function () {
                            // 以上用户都不是重新建档发卡
                            var userInfo =  tabBaseInfo.getFormData();  // $('.form-userInfo').sovals();
                            userInfo.id = null;
                            $ajax.post(b.base + '/ui/outp/patientCard/qrcodeCardNoCreateArchives', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
                                if(rst.code === '200' || rst.code === '201'){
                                    $pop.msg('建档成功！');
                                    initTab(rst.data.list);
                                    b.listenBookbuilding(rst.data.list);
                                }
                            }).error(function (err) {
                                $pop.err(err);
                            });
                            $('.new-pop').removeClass('show-pop');
                        });
                        $(".btn-bookbuildingcancel").click(function () {
                            $('.new-pop').removeClass('show-pop');
                        })

                    })();
                }

                if($("#getInfoGrid2").length >0 ) {
                    $("#getInfoGrid2").datagrid({
                        // fit: true,
                        pagination: false,
                        // fitColumns : false,
                        columns: [[
                            {title: 'id', field: 'id', width: 80, hidden: true},
                            {title: '姓名', field: 'name', width: 70},
                            {title: '性别', field: 'sexName', width: 60},
                            {title: '出生日期', field: 'birthday', width: 150},
                            {title: '联系电话', field: 'tel1', width: 110},
                            {title: '身份证号', field: 'idNumber', width: 200},
                            {title: '户口地址', field: 'contactsAddr', width: 220, align: 'left'}
                        ]],
                        onLoadSuccess: function () {
                        },
                        //url:'json/userInfo.js',
                        // height: 'auto'
                        height: 350
                        // ,offset :-55
                    });

                }
                // 条码按钮 封装
                // 弹出窗口 .popOpTemBox
                // 依赖函数 bookbuilding --》   openQRcodeInput  --》bookbuildingDone
                //                      --> initTab(res.list);

                // 条码
                $("#bookbuilding").click(function () {
                    bookbuilding();
                });
                //条码
                var bookbuilding =function () {
                    //患者信息 填写

                    // 弹出二维码输入框
                    openQRcodeInput();

                    // 后台发起 建档请求 form-userInfo
                    // promise = $.ajax();
                    //  后台逻辑1 允许建档 建档同时发卡逻辑   前台 提示发卡成功

                    // promise.done;
                    // 后台逻辑2  同名患者  --> 返回同名患者集合 --> 请选择一个患者  ---》 前台条件 都不是
                    // grid.loadData(list);


                }
                // 弹出 条码输入框
                var openQRcodeInput = function () {

                    if(!b.btnReadOnly) {
                        if (!tabBaseInfo.formValidate() ) {  // $('.form-userInfo').form('validate')
                            //如果信息不完整，直接提示并退出
                            $pop.alert('发新卡请正确填写必填信息！', null, {offset: ['22%', '26%']});
                            return;
                        }
                    }
                    var pop = null;
                    pop = layer.open({
                        type : 1,
                        title : '请手动输入条码号码',
                        zIndex :2,
                        content : $('.popOpTemBox'),
                        area : ['450px','200px'],
                        success : function () {
                            $('.popOpTemBox').addClass('popOpShow');
                            $('#qrcodeCardNo').val("").focus();

                        },
                        end : function () {
                            $('.popOpTemBox').removeClass('popOpShow');
                        }
                    });
                    // bind event
                    (function () {
                        $('#qrcodeCardNo_Submit').unbind("click");
                        $('#qrcodeCardNo_Cancel').unbind("click");
                        //event
                        // 发送  建档发卡请求
                        $('#qrcodeCardNo_Submit').click(function () {
                            // 这里需要增加  新增业务判断 分别调用 后台的查询接口 和 建档接口
                        	var userInfo =  tabBaseInfo.getFormData();  //$('.form-userInfo').sovals();
                        	var formValidate = $("#templForm").form('validate');
                            if(!formValidate) return;
                            var qrcodeCardNo = $("#qrcodeCardNo").val();
                            if(b.btnReadOnly) {
                                onlyQuery(qrcodeCardNo);

                            } else {
                                userInfo.qrcodeCardNo = qrcodeCardNo;
                                //console.log(userInfo,1111);
                                $ajax.post({
                                    url : b.base+ "/ui/outp/patientCard/qrcodeCardNo",
                                    type: "POST",
                                    data: userInfo,
                                    callback : function (res) {
                                        //console.log(res);
                                        bookbuildingDone(res.data);
                                        $("#qrcodeCardNo").val("");
                                    },
                                    error : function (err) {
                                        $pop.err(err);
                                    }
                                });
                            }
                            layer.close(pop);
                        });
                        $('#qrcodeCardNo_Cancel').click(function () {
                            layer.close(pop);
                            $("#qrcodeCardNo").val("");
                        });
                    })();
                    return pop;
                }
                //只查询 不建档
                var onlyQuery = function (qrcodeCardNo) {
                    $ajax.post({
                        url : b.base+ "/ui/outp/patientCard/qrcodeCardNoQuery",
                        type: "POST",
                        data: {qrcodeCardNo : qrcodeCardNo },
                        callback : function (res) {
                            //console.log(res);
                            $("#qrcodeCardNo").val("");
                            if(res.status == '1'){
                                initTab(res.list);
                            } else {
                                //患者建档成功
                                $pop.msg("未获取条码卡发卡患者信息");
                            }
                        },
                        error : function (err) {
                            $pop.err(err);
                        }
                    });
                }

                //条码建档后续事件
                var bookbuildingDone = function (res) {
                    if(res.status == '3') {
                        //患者建档成功
                    	$pop.msg("发卡建档成功！");
                    	initTab(res.list);
                    } else if(res.status == '2'){
                        //  返回同名患者集合 --> 请选择一个患者  ---》 前台条件 都不是
                        $('#getInfoGrid2').datagrid('loadData', res.list);
                        $('.new-pop').addClass('show-pop');
                    }else if(res.status == '1'){
                    	//检索出卡号对应的患者信息，则提示卡号存在，不再后续处理
                            //initTab(res.list);
                        	//20190509再修改，bug：2805  该问题改过被合办合没了，自4月16日版本号为15227，被覆盖回去了
                            $pop.err("卡号为："+res.qrcodeCardNo+"的卡，已存在发卡记录，请更换卡号！");
                    }
                    // bind event
                    ;(function () {
                        // if(bookbuildingDoneInit) return;
                        // bookbuildingDoneInit = true;

                        $('.btn-bookbuildingOldUser').unbind("click");
                        $(".btn-confirmAllNot").unbind("click");
                        $(".btn-bookbuildingcancel").unbind("click");
                        var qrcodeCardNo = $("#qrcodeCardNo").val();
                        // 选中以有用户
                        $(".btn-bookbuildingOldUser").show();
                        $(".btn-bookbuildingOldUser").click(function () {
                        	//console.log(res);
                        	//选择已有患者，进行发卡
                        	//检索建档发卡时， 是否输入了手动建档的卡号。  如果卡号为空则仅弹出提示信息，不进行后续操作； 若不为空则进行发卡操作
                        	if(!res.qrcodeCardNo){
                        		$pop.msg('未输入卡号，无法后续！');
                        	}else{
	                            var userInfo = $('#getInfoGrid2').datagrid('getSelected');
	                            userInfo.qrcodeCardNo = res.qrcodeCardNo;
	                            if (userInfo) {
	                                $ajax.post(b.base + '/ui/outp/patientCard/qrcodeCardNoSendCard', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
	                                    if (rst.code === '200' || rst.code === '201') {
	                                        $pop.msg('发卡成功！');
                                            initTab(userInfo);
	                                    }
	                                }).error(function (err) {
                                        $pop.err(err);
                                    });
	                            } else {
	                                $pop.msg('请选择用户行！');
	                            }
	                            $('.new-pop').removeClass('show-pop');

                        	}
                        });
                        // 以上用户全不是，给用户直接发卡
                        $(".btn-confirmAllNot").click(function () {
                            // 以上用户都不是重新建档发卡
                        	var userInfo = tabBaseInfo.getFormData(); //$('.form-userInfo').sovals();
                        	userInfo.id = null;
                        	userInfo.qrcodeCardNo = qrcodeCardNo;
                            $ajax.post(b.base + '/ui/outp/patientCard/qrcodeCardNoCreateArchives', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
                                if(rst.code === '200' || rst.code === '201'){
                                    $pop.msg('建档成功！');
                                    initTab(rst.data.list);

                                }
                            }).error(function (err) {
                                $pop.err(err);
                            });
                            $('.new-pop').removeClass('show-pop');

                        });
                        $(".btn-bookbuildingcancel").click(function () {
                            $('.new-pop').removeClass('show-pop');
                        })

                    })();

                }


                /**
                 *  读卡 就诊卡
                 *  html :  .searchHasConfirm   getInfoGrid
                 *   readTreatmentCard   ---》
                 *                  --> b.listenReadTreatmentCard  -- b.initTab       -->  ！ tabBaseInfo.setFormData(data); 代替 formloaddata
                 *                  ---> b.chkPid; 可以省略  因为在  b.initTab 中有执行
                 *                   -- 》 hideInfoConfirmBox    -- 》 openPaymentLayerOrsetCheck  弹出支付页面 可能多个地方调用
                 *   这受requirejs 限制无法注入单例 只能使用一个重复的类实例 调用tabBaseInfo类方法
                 *
                 *   包括读 社保卡 身份证 就诊卡的物理卡号 去查询已有的患者信息
                 *
                 */
                // 读 就诊卡 按钮 JS 的调用逻辑
                var btn = $btnExt.get("readCard");
                $r.readCardInit();//插入读卡ocx html
                btn && btn.el.on("click",function () {
                        $r.readMacNumber(function(cardInfo){
                            // tabBaseInfo.reset()
                            // alert("cardNumber="+cardNumber);
                          //1为m1卡标志，2为二代证标志，3为社保卡标志，4为其他卡标志
                           var cardTypeArr =['','m1卡','二代身份证','社保卡','其他卡']
                            var cardType = cardInfo.type;
                            var cardNumber = cardInfo.no;
                            if(!cardNumber) return $pop.alert("我院尚不支持使用该卡，请联系系统管理员。");
                            idCard = cardInfo.no;
                            idCard_Type =cardInfo.type;
                          // alert('类型：'+cardTypeArr[cardType]+'，ID:'+cardNumber);
                            $.ajax({
                                url : b.base + "/ui/outp/patientCard/readVisitCard",
                                type: "POST",
                                data: { cardNumber : cardNumber },
                                success : function (res) {
                                    if(pagePath == "inpReg") { // 这里单独使用 住院只读卡.
                                        readTreatmentCard(res,cardType);
                                    } else {
                                        afterReadTreatmentCard(res,cardType);
                                    }

                                }
                            });
                        });
                 });


                function stateAlert (msg) {
                    if(b.btnReadOnly) {
                        $pop.alert('该卡未登记，请在诊疗卡管理菜单发新卡!', null, {offset: ['22%', '26%']});
                    } else if(typeof msg == "string") {
                        $pop.alert(msg, null, {offset: ['22%', '26%']});
                    }
                }



                //只读
               function readTreatmentCard(rst,cardType,msg) {
                    if (rst.code === '200' || rst.code === '201') {
                        var data = rst.data;
                        var state = data.state;
                        if (state == '1') {
                            //刷卡在数据库中找到用户信息，即读旧卡
                            //$('.form-userInfo').form('load', data.list);
                            // userFormLoadData(data.list);
                            // var birthday = data.list.birthday.substring(0,10);
                            // $("#age").val($T.getAgeByBir(birthday));
                            // $("#birthday").val(birthday);
                            // b.patientId = data.list.id;
                            // chkPid();
                            // getAccountBalanceByPid(b.patientId);
                            // 读到患者卡号 显示到页面中
                            initTab(data.list);
                            b.listenReadTreatmentCard(data.list);
                            return 1;//加载完信息退出操作
                        }else {
                            //window.console &&console.log(rst,cardType);
                            //非旧卡， 但卡类型为身份证
                            if (cardType == '2') {
                                //如果信息不完整，直接提示并退出
                                stateAlert("该卡未登记，且卡类型为身份证，请直接使用身份证读卡机登记信息!");
                                return 2;
                            }
                            // 该卡未登记，且卡类型为社保卡，请直接使用社保卡读卡机登记信息
                            // todo 缺少 cardType
                            // 社保卡
                            if (cardType == '3') {
                                //如果信息不完整，直接提示并退出
                                // $pop.alert('!', null, {offset: ['22%', '26%']});
                             //   $pop.alert('该卡未登记，请在诊疗卡管理菜单发新卡!', null, {offset: ['22%', '26%']});
                                stateAlert("该卡未登记，且卡类型为社保卡，请直接使用社保卡读卡机登记信息!");
                                return 2;
                            }
                            if (cardType == '4') {
                                //如果信息不完整，直接提示并退出

                                stateAlert('未知的卡类型 ，不支持该卡发卡操作!');
                                return 2;
                            }
                            //非旧卡,卡类型不为身份证
                            if (!tabBaseInfo.formValidate()) { //$('.form-userInfo').form('validate')
                                //如果信息不完整，直接提示并退出
                                // $pop.alert('该卡未登记，发新卡请正确填写必填信息！', null, {offset: ['22%', '26%']});
                                // $pop.alert('该卡未登记，请在诊疗卡管理菜单发新卡！', null, {offset: ['22%', '26%']});
                                stateAlert(msg || '该卡未登记，请在诊疗卡管理菜单发新卡！');
                                return 2;
                            }
                            return 3;
                        }

                    }
                }

               // 读就诊卡 之后的逻辑
               function afterReadTreatmentCard(rst,cardType) {
                            if(readTreatmentCard(rst,cardType,"该卡未登记，发新卡请正确填写必填信息！") != 3) return;
                            var userInfo =  tabBaseInfo.getFormData(); //$('.form-userInfo').sovals();
                            // window.console &&console.log(userInfo.id   + " --- " + b.patientId);
                            idCard = userInfo.idCard = rst.data.idCard; //读卡器读出的卡号
                            $ajax.post(b.base + '/ui/outp/patientInfo/isSendCard', userInfo, '是否为该用户发新卡？', false, false, true).done(function (rst) {//用carId发送请求到服务器端
                                if (rst.code === '200' || rst.code === '201') {
                                    var data = rst.data;
                                    var state = data.state;
                                    if(b.patientId){
                                        //就诊卡读发卡时，若页面患者信息本身就是通过搜索框检索出来的， 再点击就诊卡发卡，则按老卡用户发卡步骤操作
                                        if (userInfo) {
                                            userInfo.idCard = idCard;//把新卡卡号传递过去
                                            userInfo.cardType =idCard_Type;
                                            // window.console &&console.log(userInfo.id   + " --- " + b.patientId);
                                            $ajax.post(b.base + '/ui/outp/patientCard/sendCard', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
                                                if (rst.code === '200' || rst.code === '201') {
                                                    //$('.form-userInfo').form('load', userInfo);
                                                    userFormLoadData(userInfo,chkPid);
                                                    hideInfoConfirmBox();

                                                    // 弹出 支付 或者 勾选 押金
                                                    openPaymentLayerOrsetCheck(rst,function () {
                                                        $pop.msg('发卡成功！');
                                                    });
                                                }
                                            });
                                            return ;
                                        }
                                    }
                                    if (state == '1') {//用户在数据库里有信息档案
                                        if (data.list.length > 0) {
                                            $('.searchHasConfirm').addClass('patientInfoSearchshow');
                                            // 患者列表
                                            $('#getInfoGrid').datagrid('loadData', data.list);
                                        }
                                    }
                                    if (state == '2') {//用户在数据里无信息档案
                                        //$('#idCard').val(idCard);
                                        userInfo.cardType =cardType;
                                        $ajax.post(b.base + '/ui/outp/patientInfo/createArchives', userInfo).done(function (nrst) {
                                            //系统返回201提示为用户发卡成功,并返回b.patientId {"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555'}}
                                            if (nrst.code === '200' || nrst.code === '201') {
                                                //window.console &&console.log(data,nrst);
                                                b.patientId = nrst.data.id;
                                                $('#pid').val(b.patientId);
                                                chkPid();
                                                // 弹出 支付 或者 勾选 押金
                                                openPaymentLayerOrsetCheck(nrst,function () {
                                                    $pop.msg('发卡成功！');
                                                });
                                            }
                                        });
                                    }

                                }
                            });
                }






                // -------------------   弹窗事件   ----------------------------
                // 多用户选则事件  确定是老用户
                $('.btn-confirmOldUser').click(function () {

                    //为老用户发卡
                    var userInfo = $('#getInfoGrid').datagrid('getSelected');
                    if (userInfo) {
                        userInfo.idCard = idCard;//把新卡卡号传递过去
                        userInfo.cardType =idCard_Type;

                        // 为身份证或其他卡发卡
                        $ajax.post(b.base + '/ui/outp/patientCard/sendCard', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
                            if (rst.code === '200' || rst.code === '201') {
                                //$('.form-userInfo').form('load', userInfo);
                                userFormLoadData(userInfo,chkPid);
                                // b.patientId = userInfo.id;
                                // chkPid();
                                hideInfoConfirmBox();
                                openPaymentLayerOrsetCheck(rst,function () {
                                    $pop.msg('发卡成功！');
                                });
                            }
                        });
                    } else {
                        $pop.msg('请选择用户行！');
                    }
                });

                $('.btn-confirmNewUser').click(function () {
                    //为新用户发卡
                    var userInfo = $('.form-userInfo').sovals();
                    userInfo.idCard = idCard;//把新卡卡号传递过去
                    userInfo.cardType =idCard_Type;
                    // console.log(userInfo);
                    $ajax.post(b.base + '/ui/outp/patientInfo/createArchives', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功,并返回patientId {"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555'}}
                        if (rst.code === '200' || rst.code === '201') {
                            b.patientId = rst.data.id;
                            //patientId = rst.list.id;
                            chkPid();
                            $('#pid').val(b.patientId);
                            hideInfoConfirmBox();
                            openPaymentLayerOrsetCheck(rst,function () {
                                $pop.msg('发卡成功！');
                            });
                        }
                    });
                });

                $('.btn-colseConfirmPop').click(function () {//关闭不发卡
                    hideInfoConfirmBox();
                });

                // 隐藏 多个患者列表弹出框
                function hideInfoConfirmBox () {
                    $('.searchHasConfirm').removeClass('patientInfoSearchshow');
                }

                // 相关事件和绑定
                // 相关弹出 窗口的 逻辑
                $grid.newGrid("#getInfoGrid", {
                    // fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: 'id', field: 'id', width: 80, hidden: true},
                        {title: '姓名', field: 'name', width: 60},
                        {title: '性别', field: 'sexName', width: 50},
                        {title: '出生日期', field: 'birthday', width: 80},
                        {title: '联系电话', field: 'tel1', width: 100},
                        {title: '身份证号', field: 'idNumber', width: 180},
                        {title: '户口地址', field: 'contactsAddr', width: 180, align: 'left'}
                    ]],
                    onLoadSuccess: function () {
                    },
                    //url:'json/userInfo.js',
                    // height: 'auto'
                    height: 324
                    // ,offset :-55
                });

                // 读社保卡 按钮
                var btn2 = $btnExt.get("readCard2");
                // add new line
                /*
                1、需要先选择正确的结算费别，如果结算费别选择错误给出医保返回的提示；
                2、如果结算费别选择正确，则医保返回（统筹区号、社保卡号、身份证号、姓名、性别、出生日期、民族） done
                3、根据卡类型、社保卡号（实际为统筹区号+社保卡号）检索发卡信息表有效的发卡记录，找到patient_id，再根据patient_id检索出患者信息，如找到将患者信息显示在界面； done
                4、如果在第3步未找到已建档患者信息，再根据身份证号和姓名检索患者信息表，如找到一条或多条患者信息，由操作者人工确认是否是哪一个患者；
                5、确认患者后，提示是否绑定社保卡？绑定则为该患者插入发卡记录，卡号为社保卡号（实际为统筹区号+社保卡号）
                6、如按上述方式均未找已建档到患者，将身份证号、姓名、性别、出生日期、民族等社保接口返回的信息完成建档并显示在界面由操作人员完善其他信息，还要产生一条社保卡的发卡记录。


                二、门诊医生工作站、门诊收费、退费申请、门诊退费、票据查询、统一发票打印、门诊预交金、住院预交金、门诊发药、门诊退药、基本体查、皮试结果、检查确费、检验确费、出院结算等模块要支持读社保卡
                三、社保卡不收卡押金
                四、社保卡支持跨院读取
                五、社保卡未发过卡，将社保卡放在普通读卡器上。在门诊挂号、就诊卡管理、入院登记界面点就诊卡或身份证按钮应提示“该卡未登记，且卡类型为社保卡，请直接使用社保卡读卡机登记信息”
                六、社保卡未发过卡，将身份证放在社保卡读卡器上。在门诊挂号、就诊卡管理、入院登记界面点就诊卡或社保卡按钮应提示“该卡未登记，且卡类型为身份证，请直接使用身份证读卡机登记信息”
                * */

                var cacheMedicarePinfo = {
                    name:null,
                    idNumber:null,
                    birthday:null,
                    sex:null,
                    origin:null
                };


                btn2 && btn2.el.on("click",function () {
                    var param = {};
                    //获取结算费别
                    var allData = $('#costType').combobox('getData');
                    var costType = $('#costType').combobox('getValue');
                    var isInsure = '0';
                    // 这里???
                    for(var i=0;i<allData.length;i++){
                        if(costType == allData[i].value){
                            isInsure = allData[i].isInsure;
                        }
                    }
                    if(b.socialCardType == 1 ) {
                        tabBaseInfo.reset();

                        $r.readMacNumber(function(cardInfo){
                            //1为m1卡标志，2为二代证标志，3为社保卡标志，4为其他卡标志
                            var cardTypeArr =['','m1卡','二代身份证','社保卡','其他卡']
                            // (如为社保卡返回为卡类型标志3|身份证号码|姓名|性别代码|名族代码|出生地|出生日期)
                            var _cardInfo =  cardInfo.info;
                            var cardType =_cardInfo[0];
                            var name = _cardInfo[2];
                            var sex = _cardInfo[3].substring(0,2) == "00"?"男" : "女";
                            var nation = _cardInfo[4];
                            var birthPlace = _cardInfo[5];
                            var birthDate = _cardInfo[6];
                            idCard = _cardInfo[1]; // 发卡需要
                            cacheMedicarePinfo.name = _cardInfo[2];
                            cacheMedicarePinfo.idNumber = _cardInfo[1];
                            isIdCardNo(cacheMedicarePinfo.idNumber,"birthday,sex,age");
                            cacheMedicarePinfo.birthday = $("#birthday").val();
                            cacheMedicarePinfo.age = $("#age").val();
                            cacheMedicarePinfo.sex = $("#sex").val();
                            cacheMedicarePinfo.idCard = _cardInfo[1];
                            cacheMedicarePinfo.origin = {};
                          //  cacheMedicarePinfo.origin.insurePersonSexName =  cacheMedicarePinfo.sex;
                            cacheMedicarePinfo.origin.insurePersonName =  cacheMedicarePinfo.name;
                            cacheMedicarePinfo.origin.insureIdCard =  cacheMedicarePinfo.idNumber;

                            // todo 填写当前页面社保患者信息
                            // userFormLoadData();
                            //  isIdCardNo(pInfo.insureIdCard,)


                            userFormLoadData(cacheMedicarePinfo);
                            // cacheMedicarePinfo.birthday =  GetBirthday(pInfo.insureIdCard,{val: function () {}});

                            idCard_Type = configEnum.cardType.medicareCard;

                            // 获取患者医保信息 新增
                            setMedicalInsure();

                            readMedicareCardAction(cacheMedicarePinfo);





                        });
                        return;
                    }
                    if(isInsure=='1'){
                        //医保费别才允许读卡
                        //清空表单
                        tabBaseInfo.reset();
                        ocx.insureCall("301",costType,param).then(function ( ret) {
                            // todo 异常情况
                            // if ret.error  return $pop.alert("我院尚不支持使用该卡，请联系系统管理员。");
                            var pInfo = ret.data;
                            // //判断医保姓名是否与当前人员一致
                            // var insurePersonName = pInfo.insurePersonName;
                            // var patientName = $('#name').val();
                            // if(patientName!=insurePersonName){
                            //     $pop.alert('医保中心的姓名为['+insurePersonName+']，与当前病人姓名['+patientName+']不一致！请检查！');
                            //     return false;
                            // }
                            //显示医保人员信息
                            //医保个人编号
                            // $('#insurePersonId').val(pInfo.insurePersonId);
                            // //姓名 insurePersonName  4
                            // $('#insurePersonName').val(pInfo.insurePersonName);
                            // //性别 insurePersonSex  5
                            // $('#insurePersonSexName').val(pInfo.insurePersonSexName);
                            // //身份证号 insureIdCard  3
                            // $('#insureIdCard').val(pInfo.insureIdCard);
                            // //单位名称 corpName  26
                            // $('#corpName').val(pInfo.corpName);
                            // //社会保障卡号 insureCardNo  8
                            // $('#insureCardNo').val(pInfo.insureCardNo);
                            // //人员类型 personTypeName  9
                            // $('#personTypeName').val(pInfo.personTypeName);
                            // //参保状态 insureStatus  10
                            // $('#insureStatusName').val(pInfo.insureStatusName);
                            // //是否异地人员 elsewhereSign  11
                            // $('#elsewhereSign').val(pInfo.elsewhereSign);
                            // //个人帐户余额 lastBalance  15
                            // $('#lastBalance').val(pInfo.lastBalance);
                            // //年度 insureYear  13
                            // $('#insureYear').val(pInfo.insureYear);
                            // //本年医疗费累计 totalPay  16
                            // $('#totalPay').val(pInfo.totalPay);
                            // //本年帐户支出累计 totalAccountPay  17
                            // $('#totalAccountPay').val(pInfo.totalAccountPay);
                            // //本年统筹支出累计 totalFundPay  18
                            // $('#totalFundPay').val(pInfo.totalFundPay);
                            // //所属中心 insureName  12
                            // $('#insureName').val(pInfo.insureName);
                            // 拼装 insureCode 和 insureCardNo


                            // var MdCardParam = {
                            //     idNumber : idNumber,
                            //     name : pInfo.insurePersonName,
                            //     sex : pInfo.insurePersonSexName,
                            //     birthday: "",
                            //     idCard:pInfo.insureIdCard
                            // };
                          //  var newInsureCode =  pInfo.insureCode.substring(0,6);  // 物理卡区号 与社保中心编码可能存在不一致所以需要截取。
                           //  idCard = "" +  newInsureCode + pInfo.insureCardNo; // 取消身份证读卡
                            cacheMedicarePinfo.origin = pInfo;
                            cacheMedicarePinfo.name = pInfo.insurePersonName;
                            cacheMedicarePinfo.sex = pInfo.insurePersonSexName;
                            cacheMedicarePinfo.idNumber = pInfo.insureIdCard;
							cacheMedicarePinfo.idCard = pInfo.insureIdCard;
                            // 将读卡用的变量赋值
                            idCard =   pInfo.insureIdCard;

                            isIdCardNo(cacheMedicarePinfo.idNumber,"birthday,sex,age");
                            cacheMedicarePinfo.birthday = $("#birthday").val();
                            cacheMedicarePinfo.age = $("#age").val();
                            cacheMedicarePinfo.sex = $("#sex").val();

   							// todo 填写当前页面社保患者信息
                           // userFormLoadData();
                           //  isIdCardNo(pInfo.insureIdCard,)

                            userFormLoadData(cacheMedicarePinfo);
                           // cacheMedicarePinfo.birthday =  GetBirthday(pInfo.insureIdCard,{val: function () {}});

                            idCard_Type = configEnum.cardType.medicareCard;
                            readMedicareCardAction(cacheMedicarePinfo);
                            setMedicalInsure();

                        });
                    }else{
                        $pop.alert('非医保属性的费别不能读取医保卡！');
                    }
                    //
                });
                function readMedicareCardAction (param) {
                    // 增加医保信息表写入功能，扩展医保信息字段
                    param = $.extend(param,param.origin );
                    console.log(param);
                    $.ajax({
                        url : b.base+ "/ui/outp/patientCard/readMedicareCard",
                        type: "POST",
                        data:  param,
                        success : function (rst) {
							setTimeout(function(){//等待pid填写后触发pinyin请求
								$('#name').blur();
							},200);
                            // 类似 身份证读卡逻辑
                            // if(b.btnReadOnly) {
                            //     readTreatmentCard(res,cardType);
                            // } else {
                            //     afterReadMedicareCard(res,cardType);
                            // }
                            // function readIdCard(rst) {
                            //     //读取身份证物理id
                            //     if (rst.code === '200' || rst.code === '201') {
                            var data = rst.data;
                            var state = data.state;
                            if (state == '1') {//物理ID查到有有用户信息，即已存此身份证卡，返回完整用户信息 {"code":"200","msg":"","list":{id:'1245555',idCard :11212,documentType:1,idNumber: '10214198601140328',sex:'',birthday:'',....}}
                                // 显示社保卡用户信息
                                userFormLoadData(data.list,chkPid);
                                $("#accountBalance").val(data.account.accountBalance);
                                // 提供给外部的事件回调 不用理会
                                b.listenReadIDCard(data.list);
                                return;
                            }
                            //  点社保卡按钮读未发过卡的社保卡，系统应提示“该卡未登记，请在诊疗卡管理菜单发新卡”
                            if(b.btnReadOnly) {
                                return stateAlert();
                            }
                            if (state == '2') {//身份证号码或姓名查询返回多条信息记录，则显示在弹出的grid中，供操作者选择确认
                                idCardInfo = data._entity;
                                idCardInfo.idCard = rst.data.idCard;
                                $('.searchIdcardConfirm').addClass('patientInfoSearchshow');
                                // 患者列表
                                $('#idCardGrid').datagrid('loadData', data.list);
                                return;
                            }
                            if (state == '3') {//物理ID、身份证号码、姓名在数据库里均无记录，此时界面上用户必填信息已填写
                                idCardInfo = data.list;//{"code":"200","state":4,msg":"","data":{name:'张三',idCard :11212,documentType:1,idNumber: '10214198601140328',sex:1,birthday:'2010-01-12'}}
                                idCardInfo.idCard = rst.data.idCard;
                                //idCardId = rst.data.idCard;
                                addNewIdcard();
                            }
                        }
                    });
                }





                // 读就社保卡   之后的逻辑
                // function afterReadMedicareCard(rst,cardType) {
                //     if(readTreatmentCard(rst,cardType) != 3) return;// ui/outp/patientCard/sendMedicalCard
                //     //var userInfo = tabBaseInfo.getFormData(); //$('.form-userInfo').sovals();
                //     // window.console &&console.log(userInfo.id   + " --- " + b.patientId);
                //     //idCard = userInfo.idCard = rst.data.idCard; //读卡器读出的卡号
                //
                //     $ajax.post(b.base + '/ui/outp/patientInfo/isSendCard', cacheMedicarePinfo, '是否为该用户发新卡？', false, false, true).done(function (rst) {//用carId发送请求到服务器端
                //         if (rst.code === '200' || rst.code === '201') {
                //             // var data = rst.data;
                //             // var state = data.state;
                //             // if(b.patientId){
                //             //     // 读发卡时，若页面患者信息本身就是通过搜索框检索出来的， 再点击就诊卡发卡，则按老卡用户发卡步骤操作
                //             //     if (userInfo) {
                //             //         userInfo.idCard = idCard;//把新卡卡号传递过去
                //             //         userInfo.cardType =idCard_Type;
                //             //         // window.console &&console.log(userInfo.id   + " --- " + b.patientId);
                //             //         $ajax.post(b.base + '/ui/outp/patientCard/sendCard', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
                //             //             if (rst.code === '200' || rst.code === '201') {
                //             //                 //$('.form-userInfo').form('load', userInfo);
                //             //                 userFormLoadData(userInfo,chkPid);
                //             //                 hideInfoConfirmBox();
                //             //
                //             //                 // 弹出 支付 或者 勾选 押金
                //             //                 openPaymentLayerOrsetCheck(rst,function () {
                //             //                     $pop.msg('发卡成功！');
                //             //                 });
                //             //             }
                //             //         });
                //             //         return ;
                //             //     }
                //             // }
                //             if (state == '1') {//用户在数据库里有信息档案
                //                 if (data.list.length > 0) {
                //                     // $('.searchHasConfirm').addClass('patientInfoSearchshow');
                //
                //                     // 患者列表
                //                     // $('#getInfoGrid').datagrid('loadData', data.list);
                //                     /*
                //                     <input type="button" class="btn btn-b btn-primary btn-idcardConfirm" name="btnSubmit" value="确定是当前选择的用户">
                //                     <input type="button" class="btn btn-b btn-cancel btn-colseIdcardConfirm" name="btnCancel" value="都不是">
                //                     * */
                //                     $('.searchIdcardConfirm').addClass('patientInfoSearchshow');
                //                     // 患者列表
                //                     $('#idCardGrid').datagrid('loadData', data.list);
                //                 }
                //             }
                //             if (state == '2') {//用户在数据里无信息档案
                //                 //$('#idCard').val(idCard);
                //                 userInfo.cardType =cardType;
                //
                //                 // PatientInfo entity, PatientMedicalInsure pmi, String idCard, String cardType
                //                 var reqObj = {
                //                     cardType:configEnum.cardType.medicareCard
                //                 }
                //                 reqObj = $.extend(reqObj,cacheMedicarePinfo,cacheMedicarePinfo.origin );
                //
                //                 $ajax.post(b.base + '/ui/outp/patientInfo/medicalCreateArchives', reqObj).done(function (nrst) {
                //                     //系统返回201提示为用户发卡成功,并返回b.patientId {"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555'}}
                //                     if (nrst.code === '200' || nrst.code === '201') {
                //                         //window.console &&console.log(data,nrst);
                //                         b.patientId = nrst.data.id;
                //                         $('#pid').val(b.patientId);
                //                         chkPid();
                //                         // 弹出 支付 或者 勾选 押金
                //                         // openPaymentLayerOrsetCheck(nrst,function () {
                //                         //     $pop.msg('发卡成功！');
                //                         // });
                //                     }
                //                 });
                //             }
                //
                //         }
                //     });
                // }





                // ------------------------------  身份证读卡封装   -------------------------------------------------
                /**
                 *  html   .searchIdcardConfirm  #idCardGrid
                 *  $r.readIdCard    tabBaseInfo.reset();
                 *   readIdCard   --》  userFormLoadData
                 *    --> addNewIdcard 建新身份证
                 *
                 *
                 */
                // 读身份证
                var btn3 = $btnExt.get("readCard3");
                btn3 && btn3.el.on("click",function () {
                    $r.readIdCard(function (userInfo) {
                        tabBaseInfo.reset();
                        //默认cardType 为2 -身份证
                        var cardSex="1";
                        if(userInfo.sex =='女'){
                            cardSex="2";
                        }
                        idCard = userInfo.cardSN;
                        idCard_Type= 2;
                        var rs = $.extend({cardType:2},{idCard:userInfo.cardSN},{name:userInfo.name},{idNumber:userInfo.cardCode},
                                {birthday:userInfo.birthday},{contactsAddr:userInfo.address},{sex:cardSex});
                        $ajax.post(b.base+ '/ui/outp/patientCard/readIdCard',rs).done(readIdCard);
		                //特别 入院登记 获取患者信息的 最好不要在这里注册 公共函数
                       // window.InpRegloadForm && window.InpRegloadForm(userInfo);
                    });
                });
                // 读身份证 后逻辑 依赖 searchIdcardConfirm
                function readIdCard(rst) {
                    //读取身份证物理id
                    if (rst.code === '200' || rst.code === '201') {
                        var data = rst.data;
                        var state = data.state;
                        if (state == '1') {//物理ID查到有有用户信息，即已存此身份证卡，返回完整用户信息 {"code":"200","msg":"","list":{id:'1245555',idCard :11212,documentType:1,idNumber: '10214198601140328',sex:'',birthday:'',....}}
                            //$('.form-userInfo').form('load', data.list);
                            userFormLoadData(data.list,chkPid);
                            // var birthday = data.list.birthday.substring(0,10);
                            // $("#age").val($T.getAgeByBir(birthday));
                            // $("#birthday").val(birthday);
                            // $("#accountBalance").val(data.account.accountBalance);

                            $("#accountBalance").val(data.account.accountBalance);
                            // cardAddValidate();
                            // b.patientId = data.list.id;
                            // getAccountBalanceByPid(b.patientId);
                            b.listenReadIDCard(data.list);
                            return;
                        }
                        //点身份证按钮读未发过卡的身份证，系统应提示“该卡未登记，请在诊疗卡管理菜单发新卡”
                        if(b.btnReadOnly) {
                            return stateAlert();
                        }
                        if (state == '2') {//身份证号码或姓名查询返回多条信息记录，则显示在弹出的grid中，供操作者选择确认
                            idCardInfo = data._entity;
                            idCardInfo.idCard = rst.data.idCard;
                            //idCardId = rst.data.idCard;
                            $('.searchIdcardConfirm').addClass('patientInfoSearchshow');
                            // 患者列表
                            $('#idCardGrid').datagrid('loadData', data.list);
                            return;
                        }
                        if (state == '3') {//物理ID、身份证号码、姓名在数据库里均无记录，此时界面上用户必填信息已填写
                            idCardInfo = data.list;//{"code":"200","state":4,msg":"","data":{name:'张三',idCard :11212,documentType:1,idNumber: '10214198601140328',sex:1,birthday:'2010-01-12'}}
                            idCardInfo.idCard = rst.data.idCard;

                            //idCardId = rst.data.idCard;
                            addNewIdcard();
                        }

                    }
                }



                // 新增身份证
                function addNewIdcard() {
                    var userI = {
                        name: idCardInfo.name,
                        document_type: idCardInfo.document_type,
                        idNumber: idCardInfo.idNumber,
                        sex: idCardInfo.sex,
                        birthday: idCardInfo.birthday,
                        contactsAddr:idCardInfo.contactsAddr
                    }
                    //$('.form-userInfo').form('load', userI);//
                    userFormLoadData(userI);
                    // var birthday = userI.birthday.substring(0,10);
                    // $("#age").val($T.getAgeByBir(birthday));
                    // $("#birthday").val(birthday);
                    // if (!$('.form-userInfo').form('validate')) {//如果信息不完整，直接提示并退出
                    //     $pop.msg('身份证卡未登记，请填写正确信息后建档！', null, {offset: ['22%', '26%']});
                    //     return;
                    //  }

                    if(b.btnReadOnly) return;

                    var userInfo = tabBaseInfo.getFormData();  //$('.form-userInfo').sovals();
//                    userInfo.idCard = idCard;
//                    userInfo.idCard =  userInfo.insureIdCard || idCard;
                    userInfo.idCard = idCard;
                    userInfo.cardType =idCard_Type;
                    if(idCard_Type == configEnum.cardType.medicareCard ) {

                        userInfo.cardType =configEnum.cardType.medicareCard;
                        // // PatientInfo entity, PatientMedicalInsure pmi, String idCard, String cardType
                        // var reqObj = {
                        //     cardType:configEnum.cardType.medicareCard
                        // }
                        userInfo = $.extend(userInfo,cacheMedicarePinfo.origin );
                        $ajax.post(b.base + '/ui/outp/patientInfo/medicalCreateArchives',  userInfo, '是否用此社保卡为患者 '+userInfo.name+' 建档并绑定就诊卡？').done(function (rst) {
                            //系统返回201提示为用户发卡成功,并返回b.patientId {"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555'}}
                            b.patientId = rst.data.id;
                            //$('.form-userInfo').form('load', data.list);//将idCard load到表单中
                            userFormLoadData(rst.data);
                            b.listenReadIDCard(rst.data);
                            chkPid();
                            $pop.msg('发卡成功！');
                        });
                    } else {
                        $ajax.post(b.base + '/ui/outp/patientInfo/createArchives', userInfo, '是否用此身份证为患者 '+userInfo.name+' 建档并绑定就诊卡？',
                            {cancelback : function () {
                                //debugger;
								setTimeout(function(){//等待pid填写后触发pinyin请求
									$('#name').blur();
								},200);
                            }}).done(function (rst) {//{"code":"201","msg":"为该用户发卡成功！","list":{b.patientId:'1245555',idCard :11212}}
                            if (rst.code === '200' || rst.code === '201') {
                                b.patientId = rst.data.id;
                                //$('.form-userInfo').form('load', data.list);//将idCard load到表单中
                                userFormLoadData(rst.data.entity);
                                b.listenReadIDCard(rst.data);
                                chkPid();
                                $pop.msg('发卡成功！');
                            }
                        });
                    }

                }

                //读取身份证处理--------------------- 绑定事件
                $grid.newGrid("#idCardGrid", {
                    // fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: 'id', field: 'id', width: 80, hidden: true},
                        {title: '姓名', field: 'name', width: 60},
                        {title: '性别', field: 'sexName', width: 50},
                        {title: '出生日期', field: 'birthday', width: 80},
                        {title: '联系电话', field: 'tel1', width: 100},
                        {title: '身份证号', field: 'idNumber', width: 180},
                        {title: '余额', field: ' ', width: 50},
                        {title: '户口地址', field: 'contactsAddr', width: 180, align: 'left'},

                    ]],
                    onLoadSuccess: function () {
                    },
                    //url:'json/userInfo.js',
                    // height: 'auto'
                    fitHeight : false,
                    height: 324
                    // ,offset :-55
                });

                function hideIdcardConfirmBox() {
                    $('.searchIdcardConfirm').removeClass('patientInfoSearchshow');
                }






                // 选中身份证卡读取的用户
                $('.btn-idcardConfirm').click(function () {
                    var userInfo = $('#idCardGrid').datagrid('getSelected');
                    if (userInfo) {
                        //$('.form-userInfo').form('load', userInfo);

                        userFormLoadData(userInfo,chkPid);
                        //新增患者选中后查询医保信息的功能
                        setMedicalInsure();



                        //userInfo.cardId = cardId;//把新卡卡号传递过去
                        b.patientId = userInfo.id;
                        // chkPid();
                        hideIdcardConfirmBox();
                        //window.console && console.log(idCard);
                        userInfo.idCard = idCard;
                        userInfo.cardType =idCard_Type;

						cacheMedicarePinfo.patientId = userInfo.id;
						cacheMedicarePinfo.cardType=userInfo.cardType

                        if(idCard_Type == configEnum.cardType.medicareCard ) {
                            var uInfo = $.extend(cacheMedicarePinfo,cacheMedicarePinfo.origin);
                            $ajax.post(b.base + '/ui/outp/patientCard/sendMedicalCard', uInfo, '是否将此社保卡绑定为患者的就诊卡？').done(function (rst) {//将身份证物理ID传到服务器，对应患者资料，保存成一条新的卡记录，系统返回201提示为用户绑定成功,(并返回idCard) {"code":"201","msg":"绑定身份证成功！","list":{idCard :11212}}
                                if (rst.code === '200' || rst.code === '201') {
                                    $('#idCard').val(cacheMedicarePinfo.idCard);
                                    b.patientId = userInfo.id;
                                    chkPid();
                                    hideIdcardConfirmBox();
                                    //$('.form-userInfo').form('load', {idCard: idCard});//将idCard load到表单中
                                }
                            });

                        } else {
                            $ajax.post(b.base + '/ui/outp/patientCard/sendCard', userInfo, '是否将此身份证绑定为患者的就诊卡？').done(function (rst) {//将身份证物理ID传到服务器，对应患者资料，保存成一条新的卡记录，系统返回201提示为用户绑定成功,(并返回idCard) {"code":"201","msg":"绑定身份证成功！","list":{idCard :11212}}
                                if (rst.code === '200' || rst.code === '201') {
                                    $('#idCard').val(idCard);
                                    b.patientId = userInfo.id;
                                    chkPid();
                                    hideIdcardConfirmBox();
                                    //$('.form-userInfo').form('load', {idCard: idCard});//将idCard load到表单中
                                }
                            });

                        }

                    } else {
                        $pop.msg('请选择用户行！');
                    }
                });
                // 身份证查询 列表 都不是按钮
                $('.btn-colseIdcardConfirm').click(function () {
                    hideIdcardConfirmBox();
                    addNewIdcard();
                });


                // 查询患者个人信息  搜索选择控件调用
                var searchBtn = $btnSearch.get("search-grid");
                b.searchBtn =searchBtn;
                searchBtn.onLoad(function (d) {
                }).onSelect(function (idx, row) {
                    initTab(row);
                }).onHide(function () {
                    var iptVal = this.el.find("input").val();
                    //1 清空 表单
                    b.clear();
                    //2 填入姓名
                    // tabBaseInfo.setFormData({ name : iptVal});
                    $('#name').val(iptVal).blur();//替代上一行setFormData事件 longx 2020-02-12 10:36
                    // setFormData事件去除 $('#name').trigger('blur'); 因为表单加载时不需要重新获取首拼码
                    this.el.find("input").val(iptVal);
                    $T.setBirVal('');//恢复年龄为输入框状态

                    //3 并查询全拼     这里如何获取首拼有待讨论  如何解决

                });

                // 读社保卡后的 逻辑
                function readSocialCard(rst) {
                    if (rst.code === '200' || rst.code === '201') {
                        var data = rst.data;
                        var state = data.state;
                        data.list.idCard = data.idCard;
                        if (state == '1') {//唯一匹配到数据
                            //$('.form-userInfo').form('load', data.list);
                            userFormLoadData(data.list,chkPid);
                        }
                        if (state == '2') {//匹配到多条
                            //  search  grid box
                            $('.patientInfoSearchBox').addClass('patientInfoSearchshow');
                            searchBtn.$gridbox.datagrid('loadData', data.list);
                        }
                        if (state == '3') {//没有读取到完全匹配的信息
                            // cardAddValidate();
                            //$('.form-userInfo').form('load', data.list);
                            userFormLoadData(data.list);
                            $pop.confirm('是否为该患者建档并将身份证绑定为就诊卡？', function () {//确定
                                data.list = JSON.stringify(data.list);
                                //$ajax.post(b.base + '/ui/outp/patientInfo/createArchives',JSON.stringify(data),null,true).done(function (rst) {
                                $ajax.post(b.base + '/ui/outp/patientInfo/createArchives', data).done(function (rst) {
                                    if (rst.code === '200' || rst.code === '201') {
                                        //提交成功后手动操作区域，code为201时，界面自动提示
                                        b.patientId = rst.data.id;
                                        chkPid();
                                    }
                                });
                                return true;
                            }, function () {//取消
                                return true;
                            });
                        }

                    }
                }


                // tab 控制逻辑 依赖 tabChangeEvent  tabPtId
                $('#tabsA').tabs({
                    onSelect: function (title, index) {
                        var  tabId = "tabsA";
                        var tab = $('#' + tabId).tabs('getSelected');
                        var index = $('#' + tabId).tabs('getTabIndex', tab);

                        if (b.patientId !== tabPtId[tabId][index]) {
                            tabPtId[tabId][index] = b.patientId;
                            tabChangeEvent[tabId][index]();
                        };
                        // 发卡始终刷新 因为存在PID 不变的情况
                        if(index  == 2 ) {
                            tabChangeEvent[tabId][index]();
                        }
                    }
                });

                // 依赖 patientId
                var tabChangeEvent = {
                    tabsA: [//tab切换事件集合
                        function basePatientInfoChange() {//基础信息
//                        window.console && console.log('basePatientInfoChange run');
                        },
                        function examinationChange () {
                            tabBaseExamine.refresh(b);
                            //$("#examination-grid").datagrid('reload',{patientId : b.patientId});
                        },
                        function patientCardChange() {//就诊卡信息
                            // $('#grid-l-card').datagrid('reload', {patientId: b.patientId});
                            tabTreatmentCard.refresh(b);
//                        window.console && console.log('patientCardChange run');
                        },
                        function contactInfoChange() {//联系人信息
//                          window.console && console.log('contactInfoChange run');
                            tabUserContact.refresh(b);
                            // $('#grid-l-contact').datagrid('reload', {patientId: b.patientId});
                        },
                        function integralInfoChange() {//积分信息
                        },
                        function a() {//医保信息
                            // 医保不走该接口查询 而是走医保读卡查询
                             $ajax.post(b.base + '/ui/outp/patientMedicalInsure/findByPatientId', {patientId: b.patientId}).done(function (rst) {
                                 if (rst.code === '200') {
                                     try{
                                         rst.data.servantSign = ['是', '否'][rst.data.servantSign];
                                         for( var a in rst.data ) {
                                             $("#" + a ).text(rst.data[a]);
                                         }
                                     } catch (e) {
                                         console.error(e);
                                     }
                                     $('.form-insurance').form('load', rst.data);
                                 }
                             });
                        },
                        function advancePayChange() {
                            //预交金信息 更新 pid
                          //  $('#grid-l-money').datagrid('reload', {patientId: b.patientId});
                            tabPrepayGrid.refresh(b);

                        },
                        function hosInChange() {
                            //院内信息 更新 pid
                            tabHospitalPrompt.refresh(b);
                            // $('#grid-l-prompt').datagrid('reload', {patientId: b.patientId});
                        }
                    ]
                }
            });

        },
        clear: function () {
            //1.清空 基本信息表
            var b = back;
            b.patientId = null;
            // $('#pid').val("");
            b.searchBtn.ipt.val("");
            tabPtId = {
                tabsA: ['', '', '', '', '', '', '']
            };
            tabBaseInfo.reset();
            tabMedicalSurance.reset();
            b.chkPid();

            $('#grid-l-card').datagrid("loadData",{ total: 0, rows: [] });
            $('#examination-grid').datagrid("loadData",{ total: 0, rows: [] });
            $('#grid-l-contact').datagrid("loadData",{ total: 0, rows: [] });
            $('#grid-l-money').datagrid("loadData",{ total: 0, rows: [] });
            $('#grid-l-prompt').datagrid("loadData",{ total: 0, rows: [] });

        }

    }
    return back;
});

