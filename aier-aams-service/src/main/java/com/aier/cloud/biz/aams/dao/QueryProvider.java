package com.aier.cloud.biz.aams.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class QueryProvider {

    public String buildQuerySql(Map<String, Object> params){
        String queryFeild = (String) params.get("queryFeild");
        String table = (String) params.get("table");
        String cond = (String) params.get("cond");

        // 白名单验证，确保表名合法
        validateTableName(table.trim());

        return "SELECT "  + queryFeild + " FROM " + table + " WHERE " + cond;
    }

    private void validateTableName(String tableName) {
        List<String> validTables = Arrays.asList("AttachmentMaster","AuditFxtsAttachment","AuditFxtsRela","AuditFxtsReply","AuditHGEvalSys",
                "AuditRecord","AuditRecordAuditRecordDecision","AuditRecordAuditRecordSuggestion","AuditRecordBussinessType","AuditRecordDetail",
                "AuditRecordDetailAttachment","AuditRecordDetailQuesDesc","AuditRecordDetailReply","AuditRecordDetailReplyXxb","AuditRecordPerson",
                "AuditRecordPlateReply","AuditRecordRelaHGSys","AuditRecordReply","CodeMaster","DeptMaster","DigitalAuditResult","Feedbacks",
                "FxglGroupAuth","GovPunishRecord","HGRiskRandom","HospitalInfo","HospitalInfoIssue","HospitalInfoPerson","HospitalSelfAssessment",
                "HospitalSelfAssessmentDetail","ImpNotification","MedSuperv","MedSupervDetail","MedSupervDetailAttachment","MedSupervLevel",
                "MenuItem","OrgMaster","PosMaster","PosNameBussinessType","PosNameDeptCode","PreAuditDetail","PreAuditFocus","PreAuditItem",
                "PreAuditItem_Risk","RemindMsg","RemindTemplate","ReportAuthorized","Risk","RiskCollection","RiskCollectionLabel","RiskFindTemplate",
                "RiskQuestionStandard","RiskRelation","RiskScoreStandard","SERetractDetail","SecFunctionality","SecFunctionalityRole","SecObject",
                "SecObjectFunctionalities","SecRole","SecUser","SecUserBussinessType","SecUserRegion","SecUserRole","SelfEvaluation",
                "SelfEvaluationBussinessType","SelfEvaluationDetail","SelfRisk","SelfUserBussinessType","T_SYS_MODULE","T_SYS_PERMISSION",
                "T_SYS_ROLE","T_SYS_ROLE_PERMISSION","T_SYS_STAFF_INST_ROLE","UserCustomizations","UserSELevel","UserSelfDept","UserSelfEvaluation",
                "UserSelfEvaluationDetail","UserSelfRisk","UserSelfRiskLevel","WeChatRemind","WechatRest");
        if (!validTables.contains(tableName)) {
            throw new IllegalArgumentException("Invalid input param!");
        }
    }
}
