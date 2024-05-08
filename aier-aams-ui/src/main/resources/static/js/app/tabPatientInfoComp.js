define(['easygridEdit'] ,function($e){

    var back = {
        userFormLoadData : function(data){//data初始化用户信息
            if(data.birthday){data.birthday = data.birthday.split(' ')[0];}
            data.costType = data.costType?data.costType:'1';
            $('.form-userInfo').form('load',data);
            // if(data.birthday&&$('#birthday').length){$('#birthday').val(data.birthday);}
            // if(data.sex&&$('#sex').length){$('#sex').combobox('setValue',data.sex);}
        },
      baseInfoInit : function(){//根据 pid 设置 患者信息
    	   var me =this;
            $.ajax({
              url : base + "/ui/outp/patientInfo/selectById",
              data : { id : pid },
              type: "post"
            }).done(function (res) {
              // 回写 患者 base info
              //$(".form-userInfo").form("load",res);
              me.userFormLoadData(res);
              var birthday =  res.birthday.substring(0,10);
              // $("#age").val($T.getAgeByBir(birthday));
              $("#birthday").val(birthday);
              $T.setBirVal(birthday);

            });
        },
        init : function (base,pagePath,pid) {
        	 
        	window.base = base;
        	window.pagePath =pagePath;
        	window.pid =pid;
            var me = this;
            me.baseInfoInit();

            var promptEditInitData = {
                patientId: pid,
                hospital: '长沙爱尔眼科医院',
                inputer: '王天'
            };
            var specialInfoD = null;
			var specialInfoTypeD = null;
//var idCardId = null;//用来暂存身份证物理ID
            var idCardInfo = null;//用来暂存身份证号码、姓名、性别等等各种信息
            var patientId = pid;
            var tabPtId = {
                tabsA: [patientId, '', '', '', '', '', '']
            };
            var kinsfolkAppelArr = null;
            var certTypeArr = null;
            var idCard = null;

            $ajax.postSync(base + '/ui/sys/dict/getList?paraType=special_info').done(function (rst) {
                if (rst.code === '200') {
                    specialInfoD = rst.data;
                }
            });
			$ajax.postSync(base + '/ui/sys/dict/getList?paraType=special_info_type').done(function (rst) {
                if (rst.code === '200') {
                    specialInfoTypeD = rst.data;
                }
            });
            $ajax.postSync(base + '/ui/sys/dict/getList?paraType=relation').done(function (rst) {
                if (rst.code === '200') {
                    kinsfolkAppelArr = rst.data;
                }
            });
            $ajax.postSync(base + '/ui/sys/dict/getList?paraType=document_type').done(function (rst) {
                if (rst.code === '200') {
                    certTypeArr = rst.data;
                }
            });
//用户联系人信息 begin
            var contactEditInitData = {
                patientId: patientId
            };

            $(function () {
                // 读卡按钮 JS 的调用逻辑
                // var btn = $btnExt.get("readCard");
                // btn.el.on("click",function () {
                //     $out.readMacNumber(function(cardNumber){
                //         //if(!cardNumber) return alert("卡号不存在");
                //     	//alert("cardNumber="+cardNumber);
                //         $.ajax({
                //             url : base+ "/ui/outp/patientCard/readVisitCard",
                //             type: "POST",
                //             data: { cardNumber : cardNumber },
                //             success : function (res) {
                //                 readTreatmentCard(res);
                //             },
                //             error : function (err) {
                //                 readTreatmentCard(err);
                //             }
                //         });
                //     });
                // });
                // var btn2 = $btnExt.get("readCard2");
                // btn2.req(readSocialCard);
                // var btn3 = $btnExt.get("readCard3");
                // btn3.req(readIdCard);


                //用户信息查询及表单
                var $cardNo = $('#cardNo');
                var $cardType = $('#cardType');
                // var idcardRule = 'idCardNo["birthday,sex,age"]';
                function cardAddValidate() {
                    setTimeout(function () {
                        var cardType = $('#cardType').combobox('getValue');
                        $cardNo.validatebox('removeRule', 'idCardNo[""]');
                        if (cardType === '1') {
                            $cardNo.validatebox('addRule', 'idCardNo[""]');
                        }
                        // else {

                        // };
                    }, 200);
                }
                cardAddValidate();
                $('#cardType').combobox({
                    onChange: function (newV, oldV) {
                        cardAddValidate();
                    }
                });

                // $('#cardNo').focus(function () {
                //     cardAddValidate();
                // });

                //6156 【优化】门诊医生工作站，点击档案按钮，可以录入保存身份证号，联系地址。
              // $('#cardNo').blur(function () {
              //   var val = $(this).val();
              //   // window.console && console.log(val);
              //   isIdCardNo(val,"birthday,sex,age");
              //   $('.agebox').hide();
              //   $('.agebox-1').show();
              //   $form.formAEnterFun(null,'.form-userInfo');
              // });



                // 查询患者个人信息  搜索选择控件调用
                // http://localhost:9090/static/js/plus/pub.js
                // var searchBtn = $btnSearch.get("search-grid");
                // searchBtn.onLoad(function (d) {
                //     console.log("搜索患者信息返回数据：" + d);
                // }).onSelect(function (idx, row) {
                //     $('#tabsA').tabs('select', 0);
                //     $('.form-userInfo').form('load', row);
                //     patientId = row.id;
                // });


                // 读就诊卡 之后的逻辑
                function readTreatmentCard(rst) {
                    //用carId发送请求到服务器端
                    if (rst.code === '200' || rst.code === '201') {
                        var data = rst.data;
                        var state = data.state;
                        if (state == '1') {
                            //刷卡在数据库中找到用户信息，即读旧卡
                            //$('.form-userInfo').form('load', data.list);
                            me.userFormLoadData(data.list);
                            patientId = data.list.id;
                            return;//加载完信息退出操作
                        }
                        //非旧卡
                        if (!$('.form-userInfo').form('validate')) {
                            //如果信息不完整，直接提示并退出
                            $pop.msg('该卡未登记，发新卡请正确填写必填信息！', null, {offset: ['22%', '26%']});
                            return;
                        }
                        var userInfo = $('.form-userInfo').sovals();
                        idCard = userInfo.idCard = rst.data.idCard;//读卡器读出的卡号
                        $ajax.post(base + '/ui/outp/patientInfo/isSendCard', userInfo, '是否为该用户发新卡？', false, false, true).done(function (rst) {//用carId发送请求到服务器端
                            if (rst.code === '200' || rst.code === '201') {
                                var data = rst.data;
                                var state = data.state;
                                if (state == '2') {//用户在数据里无信息档案
                                    //$('#idCard').val(idCard);
                                    $ajax.post(base + '/ui/outp/patientInfo/createArchives', userInfo).done(function (nrst) {//系统返回201提示为用户发卡成功,并返回patientId {"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555'}}
                                        if (nrst.code === '200' || nrst.code === '201') {
                                            patientId = data.list.patientId;
                                            $('#pid').val(patientId);
                                        }
                                    });
                                }
                                if (state == '1') {//用户在数据库里有信息档案
                                    if (data.list.length > 0) {
                                        $('.searchHasConfirm').addClass('patientInfoSearchshow');
                                        $('#getInfoGrid').datagrid('loadData', data.list);
                                    }
                                }
                            }
                        });
                    }
                }

                // 读社保卡后的 逻辑
                function readSocialCard(rst) {
                    if (rst.code === '200' || rst.code === '201') {
                        var data = rst.data;
                        var state = data.state;
                        data.list.idCard = data.idCard;
                        if (state == '1') {//唯一匹配到数据
                            //$('.form-userInfo').form('load', data.list);
                          me.userFormLoadData(data.list);
                            // cardAddValidate();
                            patientId = data.list.id;
                        }
                        if (state == '2') {//匹配到多条
                            //  search  grid box
                            $('.patientInfoSearchBox').addClass('patientInfoSearchshow');
                            searchBtn.$gridbox.datagrid('loadData', data.list);
                        }
                        if (state == '3') {//没有读取到完全匹配的信息
                            // cardAddValidate();
                            //$('.form-userInfo').form('load', data.list);
                            me.userFormLoadData(data.list);
                            $pop.confirm('是否为该患者建档并将身份证绑定为就诊卡？', function () {//确定
                                data.list = JSON.stringify(data.list);
                                //$ajax.post(base + '/ui/outp/patientInfo/createArchives',JSON.stringify(data),null,true).done(function (rst) {
                                $ajax.post(base + '/ui/outp/patientInfo/createArchives', data).done(function (rst) {
                                    if (rst.code === '200' || rst.code === '201') {
                                        //提交成功后手动操作区域，code为201时，界面自动提示
                                        patientId = rst.data.id;
                                    }
                                });
                                return true;
                            }, function () {//取消
                                return true;
                            });
                        }

                    }
                }

                // 读身份证 后逻辑 依赖 searchIdcardConfirm
                function readIdCard(rst) {
                    //读取身份证物理id
                    if (rst.code === '200' || rst.code === '201') {
                        var data = rst.data;
                        var state = data.state;
                        if (state == '1') {//物理ID查到有有用户信息，即已存此身份证卡，返回完整用户信息 {"code":"200","msg":"","list":{id:'1245555',idCard :11212,documentType:1,idNumber: '10214198601140328',sex:'',birthday:'',....}}
                            //$('.form-userInfo').form('load', data.list);
                            me.userFormLoadData(data.list);
                            // cardAddValidate();
                            patientId = data.list.id;
                            return;
                        }
                        if (state == '2') {//身份证号码或姓名查询返回多条信息记录，则显示在弹出的grid中，供操作者选择确认
                            //{"code":"200",
                            // "msg":"向服务器提交信息成功！",
                            // "state":"3",
                            // "data":{
                            //     "name":"张三",
                            //     "idCard" :11212,
                            //     "documentType":1,
                            //     "idNumber": "653130198208203178",
                            //     "sex":1,
                            //     "birthday":"2010-01-12",
                            // },
                            //   "list" :
                            //     [{"id":"QsEDfH","name":"叶福林","documentType":2,"idNumber":"2018010534A","tel1":"16670122608","sex":1,"birthday":"2001-12-14","patientType":1,"country":1,"nationId":1,"occuType":1,"marriage":1,"costType":1,"balance":120,"contactsAddr":"这公集车整证"},
                            //     {"id":"THbNJC","name":"邓焱文","documentType":3,"idNumber":"2018020104A","tel1":"15520120018","sex":2,"birthday":"1983-04-14","patientType":1,"country":2,"nationId":2,"occuType":1,"marriage":2,"costType":2,"balance":210,"contactsAddr":"而规正口观第"}]
                            // }
                            idCardInfo = data._entity;
                            idCardInfo.idCard = rst.data.idCard;
                            //idCardId = rst.data.idCard;
                            $('.searchIdcardConfirm').addClass('patientInfoSearchshow');
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


                // 初始化 体测记录表
                $grid.newGrid("#examination-grid",{
                    fitParent: true,
                    pagination : false,
                    fitColumns : false,
                    fit: true,
                    columns:[[
                        {title:'操作',field:'op',width:70, rowspan :2 ,formatter: function (v,row,index) {
                            return '<span class="s-op s-op-del icon-del" title="删除"></span>';
                        }},
                        {title:'记录时间', align :"center",field:'createDate' ,rowspan :2 ,align :'center' ,width:140  },
                        {title:'远视力',  field:'id3', colspan:4, align :'center' },
                        {title:'近视力',  field:'id2', colspan:2, align :'center',width:100},
                        {title:'矫正视力',  field:'id4', colspan:2, align :'center' },
                        {title:'眼压mmHg',field:'id5',  colspan:2,align :'center' },
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
                    ]],

                    onDblClickRow : function (index,row) {
                    },
                    onBeforeLoad : function(data){
                        var patId =  patientId;
                        if(patId==null || patId==''){
                            return false;
                        }
                    },
                    onSelect : function (i,row) {
                        $("#examination-form").form('load',row);
                    },
                    onLoadSuccess:function(data){
                    },
                    queryParams : {
                        patientId :   patientId
                    },
                    //url:  base + '/ui/outp/nurse/getPhysicExamListByPatientId',
                    url:  base + '/ui/outp/regist/getPhysicExamListByPatientId',
                    offset :-78
                });






                // tab 控制逻辑 依赖 tabChangeEvent  tabPtId
                $('#tabsA').tabs({

                    onSelect: function (title, index) {

                        var  tabId = "tabsA";
                        var tab = $('#' + tabId).tabs('getSelected');
                        var index = $('#' + tabId).tabs('getTabIndex', tab);
                        if (patientId !== tabPtId[tabId][index]) {
                            tabPtId[tabId][index] = patientId;
                            tabChangeEvent[tabId][index]();
                        };
                    }
                });

                // 依赖 patientId
                var tabChangeEvent = {
                    tabsA: [//tab切换事件集合
                        function basePatientInfoChange() {//基础信息
//                        window.console && console.log('basePatientInfoChange run');

                            me.baseInfoInit();
                        },
                        function examinationChange () {
                            $("#examination-grid").datagrid('reload',{patientId: patientId});
                        },
                        function patientCardChange() {//就诊卡信息

                            $('#grid-l-card').datagrid('reload', {patientId: patientId});
//                        window.console && console.log('patientCardChange run');
                        },
                        function contactInfoChange() {//联系人信息
//                        window.console && console.log('contactInfoChange run');
                            $('#grid-l-contact').datagrid('reload', {patientId: patientId});
                        },
                        function integralInfoChange() {//积分信息
                        },
                        function medicInsInfoChange() {//医保信息

//                            $ajax.post(base + '/ui/outp/patientMedicalInsure/findByPatientId', {patientId: patientId}).done(function (rst) {
//                                if (rst.code === '200') {
//                                    rst.data.servantSign = ['是', '否'][rst.data.servantSign];
//                                    // $('.form-insurance').form('load', rst.data);
//                                    for( var a in rst.data ) {
//                                        $("#" + a ).text(rst.data[a]);
//                                    }
//                                }
//                            });
                        },
                        function advancePayChange() {
                            //预交金信息
                            $('#grid-l-money').datagrid('reload', {patientId: patientId});
                        },
                        function hosInChange() {
                            //院内信息
                            $('#grid-l-prompt').datagrid('reload', {patientId: patientId});
                        }
                    ]
                }



                //tab 中 grid 的初始化
                // grid-l-card 依赖
                $grid.newGrid("#grid-l-card", {
                    fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: '患者ID', field: 'patientId', hidden: true},
                        {title: '卡ID', field: 'id', hidden: true},
                        {title:'操作' ,field:'opt', width:110 ,formatter: function (value,row,index) {
                            return '<span class="s-icon s-icon-op op-lock icon-lock" title="挂失"></span> <span class="s-icon s-icon-op op-unlock icon-unlock_alt" title="解挂"></span><span class="s-icon s-icon-op op-refund icon-retweet" title="退卡"></span>';
                         }, hidden : pagePath !== "cardMgr" },
                        {title: '类型', field: 'cardTypeName', width: 80},
                        {title: '卡号', field: 'cardNumber', width: 80},
						{title: '押金', field: 'cardDeposit', width: 80},
						{title: '状态', field: 'cardStateName', width: 80},
                        {title: '发卡机构', field: 'hospName', width: 80},
                        {title: '发卡时间', field: 'issuingDate', width: 140},
                        {title: '发卡人', field: 'issuingOperatorName', width: 80},
                        {title: '备注', field: 'remarks', width: 80}
                    ]],
                    onBeforeLoad: function (param) {
                        if (!param.patientId) {
                            return false;
                        }
                    },
                    onLoadSuccess: function () {
                    },
                    url: base + '/ui/outp/patientCard/list',
                    height: 'auto'
                    // ,offset :-55
                });
                // useage 若干处 当前tab 页面点击等
                $grid.newGrid("#grid-l-contact", {
                    fit: true,
                    //checkOnSelect : false,
                    //selectOnCheck : false,
                    //singleSelect : false,
                    //ctrlSelect : true,
                    pagination: false,
                    fitColumns: false,
                    columns: [[
                        {title: 'id', field: 'id', width: 50, hidden: true},
                        {title: 'creator', field: 'creator', width: 50, hidden: true},
                        // {
                        //     title: '操作', field: 'op', width: 70, formatter: function (v, row, index) {
                        //     return '<span class="s-op s-op-del icon-del" title="删除"></span>'
                        //     //'<span class="s-op s-op-up icon-arrow-up" title="向上"></span> <span class="s-op s-op-down icon-arrow-down" title="向下"></span>'
                        // }
                        // },
                        {title: '患者', field: 'patientId', width: 50, hidden: true},
                        {
                            title: '患者关系', field: 'relation', width: 120, formatter: function (v, row, index) {
                            var rval = '';
                            $.each(kinsfolkAppelArr, function () {
                                if (this.valueCode == v) {
                                    rval = this.valueDesc;
                                    return false;
                                }
                            });
                            return rval;
                        }, editor: {
                            type: 'combobox', options: {
                                valueField: 'valueCode',
                                textField: 'valueDesc',
                                limitToList: true,
                                reversed: true,
                                data: kinsfolkAppelArr,
                                //url : base + '/ui/sys/dict/getList?paraType=relation',
                                missingMessage: '请选择患者称谓',
                                required: true
                            }
                        }
                        },
                        {title: '联系人姓名', field: 'name', width: 100, editor: {type: 'validatebox', options: {required: true}}},
                        {
                            title: '证件类型', field: 'certType', width: 120, formatter: function (v, row, index) {
                            var rval = '';
                            $.each(certTypeArr, function () {
                                if (this.valueCode == v) {
                                    rval = this.valueDesc;
                                    return false;
                                }
                            });
                            return rval;
                        }, editor: {
                            type: 'combobox', options: {
                                valueField: 'valueCode',
                                textField: 'valueDesc',
                                limitToList: true,
                                reversed: true,
                                data: certTypeArr,
                                //url : base + '/ui/sys/dict/getList?paraType=document_type',
                                onSelect: function (record) {
                                    var $code = $e.getTxt($(this), 'certNumber');
                                    if (record.valueCode == '1') {
                                        $code.validatebox('addRule', 'idCardNo[""]');
                                    } else {
                                        $code.validatebox('removeRule', 'idCardNo[""]');
                                    }
                                    ;
                                },
                                missingMessage: '请选择证件类型',
                                required: true
                            }
                        }
                        },
                        {title: '证件编码', field: 'certNumber', width: 150, editor: {type: 'diy', options: {required: true}}},
                        {title: '联系电话', field: 'tel', width: 110, editor: {type: 'validatebox', options: {required: true}}},
                        {title: '登记人', field: 'creatorName', width: 80},
                        {title: '登记时间', field: 'createDate', width: 100},
                        {title: '备注', field: 'remarks', width: 200, align: 'left', titletip:true,editor: {type: 'text'}}
                    ]],
                    onClickCell: function (index, field, value) {
                        contactEditInitData.patientId = patientId;
                        $e.editRow({
                            grid: '#grid-l-contact',
                            index: index,
                            cellField : field,
                            focusField: 'relation',
                            canAdd: true,
                            initData: contactEditInitData
                        });
                    },
                    onBeforeLoad: function (param) {
                        if (!param.patientId) {
                            return false;
                        }
                    },
                    onLoadSuccess: function () {
                    },
                    url: base + '/ui/outp/patientContacts/list',
                    height: 'auto'
                    // ,offset :-55
                });
                // #grid-l-money 初始化
                // 依赖 useage
                $grid.newGrid("#grid-l-money", {
                    fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: '交易时间', field: 'transTime', width: 80},
                        {title: '交易类型', field: 'transChangeTypeName', width: 80},
                        {title: '交易单号', field: 'transNumber', width: 80},
                        {title: '交易场景', field: 'transSceneName', width: 80},
                        {title: '交易金额', field: 'transAmount', width: 80},
                        {title: '余额', field: 'transBalance', width: 80},
                        {title: '创建时间', field: 'modifyDate', width: 80},
                        {title: '创建医院', field: 'hospName', width: 80}
                    ]],
                    onLoadSuccess: function () {
                    },
                    onBeforeLoad: function (param) {
                        if (!param.patientId) {
                            return false;
                        }
                    },
                    url: base + '/ui/trans/prepayment/getByPatientId',
                    height: 'auto'
                    // offset :0
                });

                //  院内信息 grid-l-prompt 初始化  依赖 specialInfoD  icon-del 删除
                $grid.newGrid("#grid-l-prompt", {
                    fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: 'id', field: 'id', width: 50, hidden: true},
                        {title: 'modifer', field: 'modifer', width: 50, hidden: true},
                        {title: 'hospId', field: 'hospId', width: 50, hidden: true},
                        {
                            title: '操作', field: 'op', width: 70, formatter: function (v, row, index) {
                            return '<span class="s-op s-op-del icon-del" title="删除"></span>'
                            //'<span class="s-op s-op-up icon-arrow-up" title="向上"></span> <span class="s-op s-op-down icon-arrow-down" title="向下"></span>'
                        }
                        },{
                            title: '提示类型', field: 'specialInfoType', width: 120, formatter: function (v, row, index) {
                                var rval = '';
                                $.each(specialInfoTypeD, function () {
                                    if (this.valueCode == v) {
                                        rval = this.valueDesc;
                                        return false;
                                    }
                                });
                                return rval;
                            }, editor: {
                                type: 'combobox', options: {
                                    valueField: 'valueCode',
                                    textField: 'valueDesc',
                                    limitToList: true,
                                    reversed: true,
                                    data: specialInfoTypeD,
                                    //url : base + '/ui/sys/dict/getListForComb?tag=special_info_type',
                                    missingMessage: '请选择提示类型',
                                    required: true
                                }
                            }
                        },{
                            title: '提示内容', field: 'specialInfo', width: 120, formatter: function (v, row, index) {
                                var rval = '';
                                $.each(specialInfoD, function () {
                                    if (this.valueCode == v) {
                                        rval = this.valueDesc;
                                        return false;
                                    }
                                });
                                return rval;
                            }, editor: {
                                type: 'combobox', options: {
                                    valueField: 'valueCode',
                                    textField: 'valueDesc',
                                    limitToList: true,
                                    reversed: true,
                                    data: specialInfoD,
                                    //url : base + '/ui/sys/dict/getListForComb?tag=special_info_type',
                                    missingMessage: '请选择提示内容',
                                    required: true
                                }
                            }
                        },
                        {title: '备注', field: 'remarks', width: 200, editor: {type: 'validatebox', options: {required: true}}},
                        {title: '适应范围', field: 'hospName', width: 120},
                        {title: '录入医院', field: 'hospName', width: 120},
                        {title: '录入人', field: 'modiferName', width: 80},
                        {title: '录入时间', field: 'modifyDate', width: 110}
                    ]],
                    onClickCell: function (index, field, value) {
                        promptEditInitData.patientId = patientId;
                        $e.editRow({
                            grid: '#grid-l-prompt',
                            index: index,
                            cellField : field,
                            focusField: 'content',
                            canAdd: true,
                            initData: promptEditInitData
                        });
                    },
                    onBeforeLoad: function (param) {
                        if (!param.patientId) {
                            return false;
                        }
                    },
                    onLoadSuccess: function () {
                    },
                    url: base + '/ui/outp/patientSpecialWarn/list',
                    height: 'auto'
                    // ,offset :-55
                });





                // #grid-l-contact   联系人 保存按钮
                $('.btn-contact-save').click(function () {
                    $e.ifEndEdit(function () {//判断是否可以结束编辑状态
                        // var allData = $e.getGridData('#grid-l-contact');//获取grid所有行的值
                        var updateData = $('#grid-l-contact').datagrid('getChanges', 'updated');//获取grid编辑的行值
                        var insertData = $('#grid-l-contact').datagrid('getChanges', 'inserted');//获取grid插入的值
                        var changetData = updateData.concat(insertData);
                        if (changetData.length) {//只获取变化值，回传changetData
                            $ajax.post(base + '/ui/outp/patientContacts/save', JSON.stringify(changetData), true, true).done(function (rst) {
                                if (rst.code === '200' || rst.code === '201') {
                                    $('#grid-l-contact').datagrid('reload');
                                };
                            });
                        } else {
                            $pop.msg('请填写完整联系人信息！');
                        };
                    }, '#grid-l-contact');

                });
                //  #grid-l-contact   联系人 删除按钮
                $('.userContactGridBox').on('click', '.s-op-del', function () {//删除单条
                    var row = $('#grid-l-contact').datagrid('getSelected');
                    var ix = $('#grid-l-contact').datagrid('getRowIndex', row);
                    if (row.id) {
                        $ajax.post(base + '/ui/outp/patientContacts/delete', {id: row.id}, '你确定删除此记录吗？').done(function (rst) {
                            if (rst.code === '200' || rst.code === '201') {
                                $('#grid-l-contact').datagrid('deleteRow', ix);
                            };
                        });
                    } else {
                        $('#grid-l-contact').datagrid('deleteRow', ix);
                    }
                    ;
                    return false;
                });
                //  #grid-l-contact   联系人 新增按钮
                $('.btn-contact-add').click(function () {
                    contactEditInitData.patientId = patientId;
                    $e.addNewRow({
                        grid: '#grid-l-contact',
                        focusField: 'kinsfolkAppel',
                        initData: contactEditInitData
                    });
                });


                // 读卡 回写医保信息
                $('.btn-updateInsurance').click(function () {//医保卡更新操作
                    $ajax.post(base + '/ui/outp/patientMedicalInsure/readMedicareCard').done(function (rst) {
                        if (rst.code === '200') {
                            $('.form-insurance').form('load', rst.data);
                            $ajax.post('json/true.js', rst.data).done(function (rst) {

                            });
                        }
                    })
                });

                // 院内信息 新增
                $('.btn-prompt-add').click(function () {

                    promptEditInitData.patientId = patientId;
                    $e.addNewRow({
                        grid: '#grid-l-prompt',
                        focusField: 'content',
                        initData: promptEditInitData
                    });
                });
                // 院内信息 删除
                $('.userPromptGridBox').on('click', '.s-op-del', function () {
                    //删除单条
                    var row = $('#grid-l-prompt').datagrid('getSelected');
                    var ix = $('#grid-l-prompt').datagrid('getRowIndex', row);
                    if (row.id) {
                        $ajax.post(base + '/ui/outp/patientSpecialWarn/delete', {id: row.id}, '你确定删除此记录吗？').done(function (rst) {
                            // window.console && console.log(rst);
                            if (rst.code === '200' || rst.code === '201') {
                                $('#grid-l-prompt').datagrid('deleteRow', ix);
                            }
                            ;
                        });
                    } else {
                        $('#grid-l-prompt').datagrid('deleteRow', ix);
                    }
                    ;
                    return false;
                });
                //院内信息  保存
                $('.btn-prompt-save').click(function () {
                    $e.ifEndEdit(function () {//判断是否可以结束编辑状态
                        var updateData = $('#grid-l-prompt').datagrid('getChanges', 'updated');//获取grid编辑的行值
                        var insertData = $('#grid-l-prompt').datagrid('getChanges', 'inserted');//获取grid插入的值
                        var changetData = updateData.concat(insertData);
                        // window.console && console.log(updateData, insertData, changetData);
                        if (changetData.length) {//只获取变化值，回传changetData
                            $ajax.post(base + '/ui/outp/patientSpecialWarn/save', JSON.stringify(changetData), true, true).done(function (rst) {
                                // window.console && console.log(rst);
                                if (rst.code === '200' || rst.code === '201') {
                                    $('#grid-l-prompt').datagrid('reload');
                                }
                                ;
                            });
                        } else {
                            $pop.msg('请填写院内提示！');
                        }
                        ;
                    }, '#grid-l-prompt');

                });






                // 相关弹出 窗口的 逻辑
                // 初始化



                $grid.newGrid("#getInfoGrid", {
                    // fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: 'id', field: 'id', width: 80, hidden: true}, 
                        {title: '姓名', field: 'name', width: 60},
                        {title: '性别', field: 'sex', width: 50},
                        {title: '出生日期', field: 'birthday', width: 80},
                        {title: '联系电话', field: 'tel1', width: 100},
                        {title: '身份证号', field: 'idNumber', width: 180},
                        {title: '联系地址', field: 'contactsAddr', width: 180, align: 'left'}
                    ]],
                    onLoadSuccess: function () {
                    },
                    //url:'json/userInfo.js',
                    // height: 'auto'
                    height: 324
                    // ,offset :-55
                });

                // 设置用户信息表单某些 初始值
                // setTimeout(function () {
                //     $('#cardType').combobox('setValue', '1');
                // }, 200);
                function hideInfoConfirmBox () {
                    $('.searchHasConfirm').removeClass('patientInfoSearchshow');
                }

                $('.btn-confirmOldUser').click(function () {
                    //为老用户发卡
                    var userInfo = $('#getInfoGrid').datagrid('getSelected');
                    // window.console && console.log(userInfo);
                    if (userInfo) {
                        userInfo.idCard = idCard;//把新卡卡号传递过去
                        $ajax.post(base + '/ui/outp/patientCard/sendCard', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功{"code":"201","msg":"为该用户发卡成功！"}
                            if (rst.code === '200' || rst.code === '201') {
                                //$('.form-userInfo').form('load', userInfo);
                                me.userFormLoadData(userInfo);
                                patientId = userInfo.id;
                                hideInfoConfirmBox();
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
                    $ajax.post(base + '/ui/outp/patientInfo/createArchives', userInfo).done(function (rst) {//系统返回201提示为用户发卡成功,并返回patientId {"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555'}}
                        if (rst.code === '200' || rst.code === '201') {
                            patientId = rst.list.patientId;
                            $('#pid').val(patientId);
                            hideInfoConfirmBox();
                        }
                    });
                });


                $('.btn-colseConfirmPop').click(function () {//关闭不发卡
                    hideInfoConfirmBox();
                });$grid.newGrid("#getInfoGrid", {
                    // fit: true,
                    pagination: false,
                    // fitColumns : false,
                    columns: [[
                        {title: 'id', field: 'id', width: 80, hidden: true},
                        {title: '姓名', field: 'name', width: 60},
                        {title: '性别', field: 'sex', width: 50},
                        {title: '出生日期', field: 'birthday', width: 80},
                        {title: '联系电话', field: 'tel1', width: 100},
                        {title: '身份证号', field: 'idNumber', width: 180},
                        {title: '联系地址', field: 'contactsAddr', width: 180, align: 'left'}
                    ]],
                    onLoadSuccess: function () {
                    },
                    //url:'json/userInfo.js',
                    // height: 'auto'
                    height: 324
                    // ,offset :-55
                });


                //读取身份证处理---------------------begin
                $grid.newGrid("#idCardGrid", {
                    // fit: true,
                    pagination: false,
                    // fitColumns : false,
                    fitHeight :false,
                    columns: [[
                        {title: 'id', field: 'id', width: 80, hidden: true},
                        {title: '姓名', field: 'name', width: 60},
                        {title: '性别', field: 'sex', width: 50},
                        {title: '出生日期', field: 'birthday', width: 80},
                        {title: '联系电话', field: 'tel1', width: 100},
                        {title: '身份证号', field: 'idNumber', width: 180},
                        {title: '联系地址', field: 'contactsAddr', width: 180, align: 'left'}
                    ]],
                    onLoadSuccess: function () {
                    },
                    //url:'json/userInfo.js',
                    // height: 'auto'
                    height: 324
                    // ,offset :-55
                });

                function hideIdcardConfirmBox() {
                    $('.searchIdcardConfirm').removeClass('patientInfoSearchshow');
                }

                $('.btn-idcardConfirm').click(function () {
                    var userInfo = $('#idCardGrid').datagrid('getSelected');
                    if (userInfo) {
                        //$('.form-userInfo').form('load', userInfo);
                        me.userFormLoadData(userInfo);
                        //userInfo.cardId = cardId;//把新卡卡号传递过去
                        patientId = userInfo.id;
                        hideIdcardConfirmBox();
                        userInfo.idCard = idCardInfo.idCard;
                        $ajax.post(base + '/ui/outp/patientCard/sendCard', userInfo, '是否将此身份证绑定为患者的就诊卡？').done(function (rst) {//将身份证物理ID传到服务器，对应患者资料，保存成一条新的卡记录，系统返回201提示为用户绑定成功,(并返回idCard) {"code":"201","msg":"绑定身份证成功！","list":{idCard :11212}}
                            if (rst.code === '200' || rst.code === '201') {
                                $('#idCard').val(idCard);
                                //$('.form-userInfo').form('load', {idCard: idCard});//将idCard load到表单中
                            }
                        });
                    } else {
                        $pop.msg('请选择用户行！');
                    }
                });

                $('.btn-colseIdcardConfirm').click(function () {
                    hideIdcardConfirmBox();
                    addNewIdcard();
                });
                // 新增身份证
                function addNewIdcard() {
                    var userI = {
                        name: idCardInfo.name,
                        document_type: idCardInfo.document_type,
                        idNumber: idCardInfo.idNumber,
                        sex: idCardInfo.sex,
                        birthday: idCardInfo.birthday
                    }
                    $('.form-userInfo').form('load', userI);//
                    if (!$('.form-userInfo').form('validate')) {//如果信息不完整，直接提示并退出
                        $pop.msg('身份证卡未登记，请填写正确信息后建档！', null, {offset: ['22%', '26%']});
                        return;
                    }

                    var userInfo = $('.form-userInfo').sovals();
                    userInfo.idCard = idCardInfo.idCard;
                    $ajax.post(base + '/ui/outp/patientInfo/createArchives', userInfo, '是否用此身份证为患者建档并绑定就诊卡？').done(function (rst) {//{"code":"201","msg":"为该用户发卡成功！","list":{patientId:'1245555',idCard :11212}}
                        if (rst.code === '200' || rst.code === '201') {
                            patientId = data.list.id;
                            //$('.form-userInfo').form('load', data.list);//将idCard load到表单中
                            me.userFormLoadData(data.list);
                        }
                    });
                }






              $('#birthday').blur(function(){//根据生日计算年龄
                var bir = $(this).val();
                // $('#age').val($T.getAgeByBir(bir));
                  $T.setBirVal(bir);
              });

                window.beforeSendPInfo = function () {
                    var pid = $('#pid').val();
                    if (!pid) {
                        $pop.alert('该患者未建档，请先建档发卡！');
                    } else {
                        return true;
                    }
                }
                window.afterSendPInfo = function (rst) {
                    if (rst.code == '200' || rst.code == '201') {
                        $('#pid').val(rst.data.id);
                        patientId = rst.data.id;
                    }
                }
                $('.btn-clearUserF').click(function () {
                    $('.form-userInfo').form('reset');
                    $('#cardType').combobox('setValue', '1');
                    $('#pid').val('');
                });

            });

        }
    }
    return back;
});

