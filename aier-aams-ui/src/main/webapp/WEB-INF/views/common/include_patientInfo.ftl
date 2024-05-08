
    <div id="tabsA" class="easyui-tabs" fit="true" border="false" width="100%"   >
    <div title="基本信息" class="content-one">
        <div class="pad-l10 pad-r10">
            <form class="soform form-validate form-enter form-userInfo pad-t10" method="post"
                  data-opt="{beforeCallback:'beforeSendPInfo',callback:'afterSendPInfo'}"
                  action="${base}/ui/outp/patientInfo/updateById">
                <input type="hidden" name="id" id="pid" value="${id}"/>
                <input type="hidden" name="idCard" id="idCard" value="${idCard}"/>
                <table class="table-form" cellspacing="0" cellpadding="0">
                    <tr>
                        <th width="7%">姓名</th>
                        <td width="11%"><input type="text" id="name" class="txt txt-validate" name="name" value=""
                                               noNull="请输入姓名"  maxlength='50'/></td>

                        <th width="7%">电话</th>
                        <td width="11%"><input type="text" class="txt txt-validate" name="tel1" value=""
                                               noNull="请填写电话" maxlength='15'/></td>
                        <th width="7%">病人类别</th>
                        <td width="11%">
                        [@ui_select id="visit_identity" name="patientType" tag="visit_identity" style="width:100%;" value="1" dataOptions="editable:false" filterkey="firstSpell"/]
                        </td>
                        <th width="7%">证件类型</th>
                        <td width="11%">
                        [@ui_select id="cardType" name="documentType"  tag="document_type" style="width:100%;"  dataOptions="limitToList : true,reversed:true,editable:false" filterkey="firstSpell"/]
                        </td>
                        <th width="7%">证件号码</th>
                        <td ><input id="cardNo" type="text" class="txt txt-validate" name="idNumber" maxlength='18'/></td>
                    </tr>

                    <tr>
                        <th>性别</th>
                        <td>
                        [@ui_select id="sex" name="sex" tag="sex" style="width:100%;"   class="easyui-combobox required" dataOptions="limitToList : true,reversed:true,required:true,editable:false" filterkey="firstSpell" uiShowDefault=false/]
                        </td>
                        <th>出生日期</th>
                        <td><input id="birthday" type="text" class="txt txt-validate so-date"
                                   style="width:100%;" data-opt="{maxDate:new Date()}"
                                   name="birthday" validType="normalDate" noNull="请选择出生日期"/></td>
                        <th>年龄</th>
                        <td><input id="age" type="text" class="txt txt-validate unedit" name="age" value=""
                                   readonly="readonly"/></td>
                        <th>国籍</th>
                        <td>
                        [@ui_select id="country" value="156" name="country" tag="country" style="width:100%;" dataOptions="editable:false" filterkey="firstSpell"/]
                        </td>
                        <th>民族</th>
                        <td>
                        [@ui_select id="nation" value="2" name="nation" tag="nation" style="width:100%;" dataOptions="editable:false" filterkey="firstSpell"/]
                        </td>
                    </tr>
                    <tr>
                        <th>职业</th>
                        <td>
                        [@ui_select id="occu_type" name="occuType" tag="occu_type" style="width:100%;" dataOptions="editable:false" filterkey="firstSpell"/]
                        </td>
                        <th>费别</th>
                        <td>
                        [@ui_select id="cost_type" name="costType" tag="cost_type" style="width:100%;" dataOptions="editable:false" filterkey="firstSpell"/]
                        </td>
                        <th>婚姻</th>
                        <td>
                        [@ui_select id="marriage" name="marriage" tag="marriage" style="width:100%;" dataOptions="editable:false" filterkey="firstSpell"/]
                        </td>
                        <th>可用余额</th>
                        <td><input type="text" class="txt unedit" name="accountBalance" readonly="readonly"/></td>
                        <th>联系地址</th>
                        <td  ><input type="text" class="txt" name="contactsAddr" maxlength='50'/></td>
                    </tr>


                </table>
                <p class="row-btn right">
                [#if (templatetype == 'readonly')]
                    [#else]
                    <input type="button" class="btn btn-b btn-primary btn-easyFormSubmit" name="btnSubmit"
                           value="保存">&nbsp;&nbsp;
                    <input type="button" class="btn btn-b btn-cancel btn-clearUserF" name="btnCancel"
                           value="清除">
                [/#if]
                </p>
            </form>
        </div>
    </div>
    <div title="基本体查" class="content-one">
        <div id="examination-grid"></div>
    </div>
    <div title="就诊卡" class="content-one">
        <div id="grid-l-card"></div>
    </div>
    <div title="联系人" class="content-one">

        <div class="userGridBox userContactGridBox">
            <div id="grid-l-contact"></div>
        </div>
        [#--<div class="userGridBtn">--]
            [#--<p class="row-btn right">--]
                [#--<input type="button" class="btn btn-b btn-primary btn-contact-add" name="btnSubmit"--]
                       [#--value="新增">--]
                [#--<input type="button" class="btn btn-b btn-contact-save" name="btnSubmit" value="保存">--]
            [#--</p>--]
        [#--</div>--]
    </div>
    <div title="积分" class="content-one">
    </div>
    <div title="医保信息" class="content-one">
        <div class="pad-10 pad-l10 pad-r10">
            <form class="soform form-insurance pad-t10" method="post" action="">
                <table class="table-list-a" cellspacing="0" cellpadding="0">
                    <tr>
                        <th width="10%">参保类别</th>
                        <td width="15%" id="insurType"></td>
                        <th width="10%">保险机构</th>
                        <td width="15%" id="insurOrgName"></td>
                        <th width="10%">医保个人编号</th>
                        <td width="15%" id="insurPersonId"></td>
                        <th width="10%">保险卡号</th>
                        <td id="insurNumber"> </td>
                    </tr>
                    <tr>
                        <th>险种名称</th>
                        <td id="insurName">
                        </td>
                        <th>人员类别</th>
                        <td id="insurPersonType"></td>
                        <th>账户余额</th>
                        <td id="insurAccAmount"></td>
                        <th>公务员属性</th>
                        <td id="servantSign">
                        </td>
                    </tr>
                    <tr>
                        <th>备注</th>
                        <td colspan="7" id="remarks"> </td>
                    </tr>
                </table>
            </form>
            <p class="row-btn right">
                <input type="button" class="btn btn-b btn-primary btn-updateInsurance" name="btnSubmit"
                       value="读卡">
            </p>
        </div>

    </div>
    <div title="门诊预交金" class="content-one">
        <div id="grid-l-money"></div>
    </div>
    <div title="院内提示" data-options="iconCls:'icon-exclamation red'" class="content-one">
        <div class="userGridBox userPromptGridBox">
            <div id="grid-l-prompt"></div>
        </div>
        <div class="userGridBtn">
            <p class="row-btn right">
                <input type="button" class="btn btn-b btn-primary btn-prompt-add" name="btnSubmit"
                       value="新增">
                <input type="button" class="btn btn-b btn-prompt-save" name="btnSubmit" value="保存">
            </p>
        </div>
    </div>
</div>





    <div class="searchIdcardConfirm">
        <p class="p-alert">通过身份证信息，在数据库里找到以下用户记录，以下是否有当前用户的信息，请选择确认。</p>
        <div id="idCardGrid"></div>
        <p class="row-btn center">
            <input type="button" class="btn btn-b btn-primary btn-idcardConfirm" name="btnSubmit" value="确定是当前选择的用户">
            <input type="button" class="btn btn-b btn-cancel btn-colseIdcardConfirm" name="btnCancel" value="都不是">
        </p>
    </div>
    <div class="searchHasConfirm">
        <p class="p-alert">通过输入信息，在数据库里找到以下用户信息，是否有当前用户的信息，请选择后为用户发卡。</p>
        <div id="getInfoGrid"></div>
        <p class="row-btn center">
            <input type="button" class="btn btn-b btn-primary btn-confirmOldUser" name="btnSubmit" value="为选中的用户发卡">
            <input type="button" class="btn btn-b btn-primary btn-confirmNewUser" name="btnSubmit" value="以上无此用户，开新卡">
            <input type="button" class="btn btn-b btn-cancel btn-colseConfirmPop" name="btnCancel" value="不发卡">
        </p>
    </div>