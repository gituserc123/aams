define(['ocxControl'],function (ocx) {
  var back  ={
    readCardInit : function(){
      var me = this;
      me.readCardPattern =  window.readCardPattern == "2" ? true : false; // true 为小程序读卡   false为Ocx 读卡
      var ocxHtml = '<object classid="clsid:A7611F8B-49D4-456A-BCFE-C835897FB20F" id="iccardBs" CODEBASE="iccardBs.ocx#version=1,0,0,1" width="0" height="0"></object>';
      if (!$('#iccardBs').length) {//读取二代身份证ID
        $('body').append(ocxHtml);
        $ajax.post( base+'/ui/based/cardConfig/selectByInstId').done(function (rst) {
          // window.console && console.log(rst);
          if(rst.code==='200'){
            me.secretData = rst.data;
          }
        });
      }
    },
    secretData : null,
    readMacNumber: function (callback) {
        /*
        *
        * [    {
             * "ext":{
               * "Section":2,
               * "BlockNumber":8,
                "Pwdmode":0,
                "PassWord":"2A0B1C6D1E0F"
                 },        "input":"",
                "func":"201",
                        "ocxFunc":"3300",        "returnType":"string"    }]
        *
        * */

      var me = this;
      //{0：'usb',1:'COM1',1:'COM1',2:'COM2',3:'COM3',4:'COM4'}
       if(back.readCardPattern) {
           var sd = me.secretData;
           // window.console && console.log(sd);
           var r_card,cardInfo;
           //r_card  =iccardBs.siAeykReadCardAdd(2,8,0,"2A0B1C6D1E0F")

          // $.each(sd,function (i,v) {
           var count = 0;
           var recursionReadCard = function (errMsg) {
               console.log("---- count : " + count);
               if(count == sd.length) return $pop.alert(errMsg);
               var v = sd[count];
               var kt = String(v.secretKeyType).toLowerCase() == 'a' ? 0 : 1;
               var blockNumber = v.section * 4 + v.blockNumber * 1;

               Input = [{
                   "ext": {
                       "Section": v.section,// ,
                       "BlockNumber": blockNumber,// blockNumber,
                       "Pwdmode": kt,//kt,
                       "PassWord": v.secretKeyValue// 这里需要 尝试两个 secretKeyValue
                   },
                   "input": "",
                   "func": "iccardBs",
                   "ocxFunc": "iccardBs",
                   "returnType": "string"
               }];
               ocx.wsCall(Input, 'iccardBs', '2', true, 30, function (msg) {
                   var data = JSON.parse(msg.input);
                   var isData = data[0].output.indexOf('|');
                   if (data[0].errMsg || isData < 0 ) {
                       count++;
                       recursionReadCard(data[0].errMsg || data[0].output );
                   } else  {
                       cardInfo = data[0].output.split('|');
                       callback && callback({type: cardInfo[0], no: cardInfo[1], info: cardInfo});
                   }
               });
           }
           recursionReadCard();
          // });



           // $.each(sd,function (i,v) {
           //     var kt = String(v.secretKeyType).toLowerCase()=='a'?0:1;
           //     var blockNumber = 0  // 用于浏览器ocx 读卡方式  v.section*4+v.blockNumber*1;
           //
           // });
       } else {
           try {
               var iccardBs = document.getElementById ("iccardBs");
               //var interfaceType = 0;
               //var r_card  =iccardO.iIccOpenPortBs(interfaceType);
               // var r_card  =iccardBs.siAeykReadCard();
               //扇区号、块号、秘钥类型、秘钥值
               // {
               // blockNumber :(0～15),
               // section : (0～63),
               // secretKeyType :(A,B),//(0,1)
               // secretKeyValue : ''
               // }
               var sd = me.secretData;
               // window.console && console.log(sd);
               var r_card,cardInfo;
               //r_card  =iccardBs.siAeykReadCardAdd(2,8,0,"2A0B1C6D1E0F")
               $.each(sd,function (i,v) {
                   var kt = String(v.secretKeyType).toLowerCase()=='a'?0:1;
                   var blockNumber = v.section*4+v.blockNumber*1;

                   //  r_card = iccardBs.siAeykReadCardNew(v.section,blockNumber,kt,v.secretKeyValue);
                   r_card = iccardBs.siAeykReadCardNewAdd(v.section,blockNumber,kt,v.secretKeyValue);
                   if(r_card==0){
                       // alert(iccardBs.AeykCardData);
                       cardInfo = iccardBs.AeykCardData.split('|');
                       callback&&callback({type:cardInfo[0],no:cardInfo[1],info:cardInfo});
                       return false;
                   }
               });

               if(r_card!=0){
                   $pop.alert("读取失败，请确定读卡器插入正确或卡片是否正确放置~");
               }
           } catch (e) {
               $pop.alert('ocx插件没有正常运行，请检查IE浏览器设置是否正确~');
           }
       }

    },
    readIdCard : function (callback,activeId) {

      if(back.readCardPattern) {
          Input = [{
              "ext":{},
              "input":"",
              "func":"IdrControl",
              "ocxFunc":"IdrControl",
              "returnType":"string"
          }];
          //
          ocx.wsCall(Input,'IdrControl','2',true,30,function (msg) {
              var data = JSON.parse(msg.input);
              if(data[0].errMsg){
                  $pop.alert(data[0].errMsg);
              }else{
                  var extInfo = JSON.parse(msg.input)[0].ext;
                  // cardtype 判断 已经在ocx小程序封装里面做了
                  var extUserInfo = extInfo.UserInfo;
                  var userInfo = {
                      name :extUserInfo.Name,//姓名
                      nation : extUserInfo.Nation,//民族
                      sex : extUserInfo.Sex,//性别
                      birthday : extUserInfo.Birthday.replace(/年|月/g, "-").replace(/日/g,""),//生日
                      cardCode : extUserInfo.CardCode,//身份证号码
                      address : extUserInfo.Address,//地址
                      agency : extUserInfo.Agency,//签发机关
                      valid : extUserInfo.Valid,//有效日期
                      SAMID : extUserInfo.SAMID,//安全模块号
                      cardSN : extUserInfo.CardSN,//身份证卡号
                      photo : extUserInfo.Photo//照片(Base64编码)
                  };
                  callback&&callback(userInfo);
              }
          })

      } else {
          var actId = activeId || 'IdrControl';
          var $act = document.getElementById(actId);
          var result,cardtype;
          try {
              var ax = new ActiveXObject("IDRCONTROL.IdrControlCtrl.1");
          } catch(e) {
              $pop.alert("控件未安装");
          }
          //result=$act.RepeatRead(1);   //设置是否重复读卡  0-不重复  1-重复
          //result=$act.setMute(1);   //设置是否静音读卡  0-不静音  1-静音

          //注意：第一个参数为对应的设备端口，USB型为1001，串口型为1至16
          result= $act.ReadCard("1001","c:\\ocx\\test.jpg");
          //result=$act.ReadCard("1001","");
          cardtype=$act.DecideReadCardType();//判断卡类型 1-身份证 2-外国居留证

          if (result==1){
              if(cardtype==1){
                  var userInfo = {
                      name :$act.GetName(),//姓名
                      nation : $act.GetFolk(),//民族
                      sex : $act.GetSex(),//性别
                      birthday : $act.GetBirthYear()+'-'+$act.GetBirthMonth()+'-'+$act.GetBirthDay(),//生日
                      cardCode : $act.GetCode(),//身份证号码
                      address : $act.GetAddress(),//地址
                      agency : $act.GetAgency(),//签发机关
                      valid : $act.GetValid(),//有效日期
                      SAMID : $act.GetSAMID(),//安全模块号
                      cardSN : $act.GetIDCardSN(),//身份证卡号
                      photo : 'data:image/jpeg;base64,' + $act.GetJPGPhotobuf ()//照片(Base64编码)
                  };
                  callback&&callback(userInfo);
                  return userInfo;
              }else{
                  $pop.alert("当前卡片不是身份证，请将身份证放到读卡器上！");
              }
          }else{
              if (result==-1){$pop.alert("端口初始化失败！");}
              if (result==-2){$pop.alert("请重新将卡片放到读卡器上！");}
              if (result==-3){$pop.alert("读取数据失败！");}
              if (result==-4){$pop.alert("生成照片文件失败，请检查设定路径和磁盘空间！");}
          }


      }


    },
    readICCard : function(callback,activeId){
      var actId = activeId || 'IdrControl';
      var $act = document.getElementById(actId);
      var result;//注意：参数为对应的设备端口，iDR210为8159，iDR200 USB型为1001，iDR200串口型为1至16
      result = $act.ReadICCard("8159");
      if (result==1){
        $pop.alert($act.GetICCardSN());
        result = $act.FindICCard();  //读写扇区前， 需调用该接口找卡
        if(result==1||result==3||result==4){  //1：M1-S50卡 2：CPU卡 3：M1-S70卡 4：Mifare UltraLight卡片
          //读IC卡
          result=$act.ReadTypeABlock(0,6,1,"ffffffffffff"); //如果为Mifare UltraLight卡片,则只需传第二个参数BID(块区),其它参数可设为0或空值
          callback&&callback(result);
          $pop.alert(result); //失败返回“0”， 成功则返回数据
          //写IC卡
          //如果为Mifare UltraLight卡片,则只需传第二个参数BID(块区),和第五个参数data， 其它参数可设为0或空值
          //result=$act.WriteTypeABlock(0,6,0," ", "55667788");
          /*if(result==1)
              alert("写IC卡成功");*/
        }
      }else{
        if (result==-1){$pop.alert("端口初始化失败！");}
        if (result==-2){$pop.alert("读IC卡失败");}
      }
    }
  }
  return back;
});