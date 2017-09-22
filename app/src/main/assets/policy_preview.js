var headerWebview=null;
/**
 * 监听上一步事件
 */
window.addEventListener('preEvent', function() {
	mui.plusReady(function(){

		mui.fire(plus.webview.getWebviewById("menuWebview"),"jumpPreEvent",{
			result:true
		});
	});
})
/**
 * 监听下一步事件
 */
window.addEventListener('nextEvent', function() {
	mui.plusReady(function(){
			mui.fire(plus.webview.getWebviewById("menuWebview"),"jumpNextEvent",{
				result:true
			});
	});
})
mui.plusReady(function(){
	headerWebview=plus.webview.getWebviewById("insurance_header.html");
	var save=document.getElementById("save");
	window.addEventListener("ncl_next",function(event){
		//通过event.detail可获得传递过来的参数内容
		save.click();
	});
	var in_ho=[];
	var in_person=new Object();
	in_person.message=getInsureOption("key",0);
	in_person.flags = "insure";
	var ho_person=new Object();
	ho_person.message=getHolderOption("key");
	ho_person.flags = "holder";
	in_ho.push(in_person);
	in_ho.push(ho_person);
	//展示投保人和被保险人的基本信息
	showBasicData(in_ho);
	var benesOption=getInsureBenes("key",0);
	//展示受益人的信息
	showBeneinfos(benesOption,in_person.message);
	//投保事项(展示投保险种信息)
	showCalInfos();
	//特别申请
	showSpecialApplication();
	init();
	//投保告知
	showImpartHasIntell();
	var salemanObj = getSalemanOption("key");
	showSalemaninfos(salemanObj);
});
//向缓存中保存数据
function saveData() {
	//如果数据保存成功触发事件‘jumpEvent’，并且传递的json数据为{result:true}
	//如果数据保存失败，传递的json数据为{result:false}
	mui.fire(headerWebview, 'jumpEvent', {result: true});
}
//将缓存中的数据读取出来显示在页面上
function readData() {
	//读取数据显示在页面上
}
function showBasicData(allInfos) {
	var policy=getPolicy("key");
	$("#policy_no").append(policy.policy_no);
	//预约回访时间
	var appointmentVal = policy.appointment_date;
	var dialectVal = policy.is_dialect;
	if(appointmentVal!=null){
		var appointmengValArray=appointmentVal.split(",");
		$("[name='appointment_date']").each(function(){
			for(var k=0;k<appointmengValArray.length;k++){
				if(appointmengValArray[k]==$(this).attr("data-value")){
					this.checked=true;
				}
			}
		});
	}
	if(dialectVal!=null){
		$("[name='is_dialect']").each(function(){
			if(dialectVal=="1"){
				this.checked=true;
			}else if(dialectVal=="0"){
				this.checked=false;
			}
		});
	}
	var pageInfos = [];
	for (var k = 0; k < allInfos.length; k++) {
		var option = allInfos[k].message;
		var startDate = "";
		var endDate = "";
		var occupationCodeVal="";
		var jobVal="";
		var childVal="";
		var countyVal="";
		var detailAddressVal="";
		var policyService="";
		var emailService="";
		var confirmTel ="";
		var telephone="";
		for (var i = 0; i < option.length; i++) {
			var pageObj = new Object();
			if (option[i].code == "certStartDate") {
				startDate = option[i].val_text;
			} else if (option[i].code == "certEndDate") {
				endDate = option[i].val_text;
				if(endDate==""){
					endDate="长期";
				}
			}else if(option[i].code == "occupationCode"){
				occupationCodeVal=option[i].val_text;
			}else if(option[i].code == "job"){
				jobVal=option[i].val_text;
			}else if(option[i].code == "isAnyChild"){
				childVal=option[i].val_text;
			}else if(option[i].code == "county"){
				countyVal= option[i].val_text;
			}else if(option[i].code == "detailAddress"){
				detailAddressVal= option[i].val_text;
			}else if(option[i].code=="telephone"){
			    telephone=option[i].val_text;
			}else {
				pageObj.code = allInfos[k].flags + "_" + option[i].code;
				pageObj.val = option[i].val_text;
			}
			//电子保单服务
			if(allInfos[k].flags=="holder" && option[i].code == "email"){
				policyService=option[i].val_text;
			}
			//电子函件服务
			if(allInfos[k].flags=="holder" && option[i].code == "emailService"){
				emailService=option[i].val;
			}
			//手机提示
			if(allInfos[k].flags=="holder" && option[i].code == "confirmTel" && option[i].val =="1"){
				confirmTel = true;
			}
			pageInfos.push(pageObj);
		}
		
		var o1=new Object();
		o1.code=allInfos[k].flags+"_"+"certStartDate";
		o1.val=startDate+"至"+endDate;
		pageInfos.push(o1);
		//职务
		var o2=new Object();
		o2.code=allInfos[k].flags+"_"+"occupationCodeAndJob";
		if(jobVal==""){
			o2.val=occupationCodeVal;
		}else {
			o2.val=occupationCodeVal+"/"+jobVal;
		}
		pageInfos.push(o2);
		var o3=new Object();
		o3.code=allInfos[k].flags+"_"+"detailAddress";
		detailAddressVal=detailAddressVal.replace(new RegExp(/(&)/g),"");
		o3.val=countyVal+detailAddressVal;
		pageInfos.push(o3);
		//子女状况
		var o4=new Object();
		o4.code = allInfos[k].flags+"_isAnyChild";
		if(childVal != ""){
			o4.val = "/"+childVal;
		}else{
			o4.val = "";
		}
		pageInfos.push(o4);
		//固定电话
		var o5=new Object;
		o5.code=allInfos[k].flags+"_telephone";
		if(telephone!=""){
		    o5.val="/"+telephone;
		}else{
		    o5.val="";
		}
        pageInfos.push(o5);
	}
	for (var j = 0; j < pageInfos.length; j++) {
		var oldVal=$("#" + pageInfos[j].code).text();
		$("#" + pageInfos[j].code).text(oldVal+pageInfos[j].val);
	}
	//电子保单
	if(policyService != ""){
		$("#policyService").attr('checked',true);
	}
	//电子函件
	if(emailService == "1"){
		$("#emailService").attr('checked',true);
	}
	//手机提示语
	if(confirmTel){
		$("#holder_mobile").append("<br>投保人已确认提供的手机号码真实、有效，为本人使用。");
	}
	//投保人财产告知
	var holderFianceImpart_array = getHolderFI("key");
	for(var a=0;a<holderFianceImpart_array.length;a++){
		var version=holderFianceImpart_array[a].version;
		var code=holderFianceImpart_array[a].code;
		var id=version+"_"+code;
		if(id=="59_001"){
			var holder_income_source=holderFianceImpart_array[a].val;
			var holder_year_income=getYearIncome(holder_income_source.split(",")[0]);
			$("#holder_59_001").text(holder_year_income);
			//收入来源
			var holder_year_income_source=getIncomeSource(holder_income_source.split(","));
			$("#holder_year_income_source_val").text("/"+holder_year_income_source);
		}
		//居民类型
		if(code == "002"){
			var resident_type=holderFianceImpart_array[a].val;
			$("#holder_59_002").text(getResidentType(resident_type));
		}
		//社保
		if(code == "003"){
			var social_security = holderFianceImpart_array[a].val;
			if (social_security == "N") {
				social_security ="否";
			} else{
				social_security ="是";
			}
			$("#holder_59_003").text(social_security);
		}
	}
	//被保人财产告知
	var insureFianceImpart_array=getInsureFI("key",0);
	for(var a=0;a<insureFianceImpart_array.length;a++){
		var version=insureFianceImpart_array[a].version;
		var code=insureFianceImpart_array[a].code;
		var id=version+"_"+code;
		if(id=="59_001"){
			var insure_income_source=insureFianceImpart_array[a].val;
			var insure_year_income=getYearIncome(insure_income_source.split(",")[0]);
			$("#insure_59_001").text(insure_year_income);
			//收入来源
			var insure_year_income_source=getIncomeSource(insure_income_source.split(","));
			$("#insure_year_income_source_val").text("/"+insure_year_income_source);
		}
		//居民类型
		if(code == "002"){
			var resident_type = insureFianceImpart_array[a].val;
			$("#insure_59_002").text(getResidentType(resident_type));
		}
		//社保
		if(code == "003"){
			var social_security = insureFianceImpart_array[a].val;
			if (social_security == "N") {
				social_security ="否";
			} else{
				social_security ="是";
			}
			$("#insure_59_003").text(social_security);
		}
	}
}
//显示受益人信息	
function showBeneinfos(beneArray,in_person) {
	//是否是南京医惠宝四款产品
	var cal_para=getLocalObj("cal_para");
	//if(cal_para==null)return;
	var para_insure_bene_same ='0';//1投、被保险人、受益人均为同一人,0不是
	for(var i =0;i<cal_para.length;i++){
		if(cal_para[i].code=='para_insure_bene_same'){
			para_insure_bene_same=cal_para[i].val;
			break;
		}
	}
	if(para_insure_bene_same=='1'){
		//document.getElementById("relationCode").style.fontWeight="bold"
		document.getElementById("relationCode").innerText="本产品无身故责任，保险金受益人默认为被保险人本人";
		document.getElementById("beneRow").style.display='none';
		return;
	}
	var rtCode = beneArray[0].relationCode;
	if (rtCode == "1") {
		$(".beneinfo-table .td_tl").css("width","170px");
		$("#relationCode").html("身故保险金受益人为被保险人的法定继承人");
		$("#beneMessage").append("<tr><td colspan='2'>注：其他特殊指定承保后可通过保全变更。</td></tr>");
		return;
	} else {
		var len = 1;
		var in_name ="";
		//获取受益人
		for (var i = 0; i < in_person.length; i++) {
			if(in_person[i].code == "name"){
				in_name = in_person[i].val;
			}
		}
		$("#relationCode").html("指定下列人员为<span> "+ in_name +" </span>的身故受益人").attr("colspan",5);
		$("#beneRow").attr("width","10px");
		for (var i = 0; i < beneArray.length; i++) {
			//受益人信息
			var beneOption =beneArray[i].option;
			//关系
			var relationName = beneArray[i].relationName;
			var certStartDateVal="";
			var certEndDateVal="";
			var professionVal="";
			var occupationCodeVal="";
			//住址
			var detailAddressVal="";
			//邮政编码
			var zipNoVal="";
			var countyVal="";
			var tr_1 = $("<tr>");
			var tr_2 = $("<tr>");
			var tr_3 = $("<tr>");
			var tr_4 = $("<tr>");
			var tr_5 = $("<tr>");
			var tr_6 = $("<tr><td colspan='6'>注：其他特殊指定承保后可通过保全变更。</td></tr>");
			var td_name = $("<td>");
			td_name.attr("colspan",2);
			var td_sex = $("<td>");
			var td_birthdate = $("<td>");
			var td_nationality = $("<td>");
			var td_certificateType = $("<td>");
			var td_title = $("<td>");
			var td_detailAddress = $("<td>");
			var td_certificateNo = $("<td>");
			var td_certStartDate = $("<td>");
			var td_relation = $("<td>");
			var td_beneOrder = $("<td>");
			var td_mobile = $("<td>");
			//受益份额
			var td_income_ratio = $("<td>");
			//职业名称/编码
			var td_profession = $("<td>");
			for (var k = 0; k < beneOption.length; k++) {
				var cal_code = beneOption[k].code;
				var cal_val = beneOption[k].val_text;
				switch (cal_code) {
					case "name":
						td_name.text("姓名："+cal_val);
						break;
					case "sex":
						td_sex.text("性别："+cal_val);
						break;
					case "birthdate":
						td_birthdate.text("出生日期："+cal_val);
						break;
					case "nationality":
						td_nationality.text("国籍："+ cal_val);
						break;
					case "certificateType":
						td_certificateType.text("证件名称："+ cal_val);
						break;
					case "certificateNo":
						td_certificateNo.text("证件号码："+cal_val);
						break;
					case "certStartDate":
						certStartDateVal = cal_val;
						break;
					case "certEndDate":
						certEndDateVal = cal_val;
						break;
					case "mobile":
						td_mobile.text("联系电话："+cal_val);
						break;
					case "detailAddress":
						detailAddressVal=cal_val;
						break;
					case "county":
						countyVal=cal_val;
						break;
					case "zipNo":
						zipNoVal=cal_val;
						break;
					case "profession":
						professionVal = cal_val;
						break;
					case "occupationCode":
						occupationCodeVal = cal_val;
						break;
					case "insuranRelation":
						td_relation.text("与被保险人关系："+cal_val);
						relationName = beneOption[k].val;
						break;
					case "bnfLot":
						td_income_ratio.text("受益份额%："+cal_val+"%");
						break;
					case "bnfGrade":
						td_beneOrder.text("受益顺序："+ cal_val);
						td_title.text(cal_val);
						break;
					default:
						break;
					}
			}
			if(certEndDateVal==""){
				certEndDateVal="长期";
			}
			td_certStartDate.html("证件起始有效日期："+certStartDateVal+"至"+certEndDateVal);
			if (professionVal=="") {
				td_profession.html("职业名称/编码：");
			} else{
				td_profession.html("职业名称/编码："+professionVal+"/"+occupationCodeVal);
			}
			if(detailAddressVal==""){
				td_detailAddress.html("<span class='detailAddress'>通讯（常住）地址：</span><span class='zipNo'>邮政编码：</span>");
			}else{
			    detailAddressVal=detailAddressVal.replace(new RegExp(/(&)/g),"");
				td_detailAddress.html("<span class='detailAddress'>通讯（常住）地址："+countyVal+detailAddressVal+"</span></span><span class='zipNo'>邮政编码："+zipNoVal+"</span>");
			}
			if(td_mobile.text()==""){
				td_mobile.html("联系电话：");
			}
			//受益人如大于1个，按照序号1、2、3正常输出，否则只显示一个，且删除序列号。
			if (beneArray.length !=1 && beneArray.length >= 2) {
				tr_1.append(td_title);
				td_title.attr("rowspan",5);
			} 
			tr_1.append(td_name);
			tr_1.append(td_birthdate);
			tr_1.append(td_sex);
			tr_2.append(td_certificateType);
			tr_2.append(td_certificateNo);
			tr_2.append(td_certStartDate);
			td_certStartDate.attr("colspan",2);
			//与被保险人关系
			tr_3.append(td_relation);
			tr_3.append(td_nationality);
			//受益顺序
			tr_3.append(td_beneOrder);
			//受益份额
			tr_3.append(td_income_ratio);
			td_income_ratio.attr("colspan",2);
			
			//如果为非直系亲属增加地址、邮箱、电话、职业等参数 2016年7月29日 10:25:30 苏建东
			if (relationName != "01" && relationName != "02" && relationName !="03" && relationName !="04" && relationName !="07") {
				tr_4.append(td_detailAddress);
				td_detailAddress.attr("colspan",2)
				td_detailAddress.attr("rowspan",2);
				td_profession.attr("colspan",2);
				tr_4.append(td_profession);
				tr_5.append(td_mobile);
				td_mobile.attr("colspan",2);
			}
			$("#beneMessage tbody").append(tr_1).append(tr_1).append(tr_2).append(tr_3).append(tr_4).append(tr_5);
		}
		len = len + (beneArray.length*5);
		$("#beneRow").attr("rowspan",len);
		$("#beneMessage tbody").append(tr_6);
	}
}
/**
 * 2016年8月1日 15:47:51
 * 苏建东
 * product_name 险种名称
 * product_code 险种代码
 * product_type 保障计划类别
 * product_ins_term 保险期间
 * product_amount 保险金额
 * product_pay_term 交费方式
 * product_pay_during 交费期间(年或至周岁)
 * product_prem 保险费（期交仅指首期）
 * is_agree 是否同意续保
 */
function showCalInfos(){
	//获取保险信息
	var intelligentInsure= getIntelligentInsure("case");
	var calInfos=intelligentInsure.benefits.content;
	var calInfo_tr=$("#policy_list_info");
	calInfo_tr.append("<tr><td rowspan='19' width='10px' class='hd td_tl'>投保事项</td>	<td>险种名称</td><td>险种代码</td><td>保障计划类别</td><td>保险期间</td><td>保险金额</td><td>交费方式</td><td>交费期间(年或至周岁)</td><td>保险费(期交仅指首期)</td></tr>");
	//主险code
	var mainCode ="";
	//领取年龄
	var getyear = "";
	//领取频率
	var getintv = "";
	//领取期限
	var getendperiod = "";
	//领取方式
	var getkind = "";
	for(var k=0;k<calInfos.length;k++){
		mainCode = calInfos[0].code;
		getyear = calInfos[0].itf_getyear;
		getintv = calInfos[0].itf_getintv;
		getendperiod = calInfos[0].itf_getendperiod;
		getkind = calInfos[0].itf_getkind;
		if((calInfos[k].itf_share>0 || calInfos[k].itf_prem>0) && (calInfos[k].itf_type=="1" || calInfos[k].itf_type=="2" || calInfos[k].itf_type=="3")){
			var tr_cal;
			var product_name;
			var product_code;
			var product_type;
			var product_ins_term;
			var product_amount;
			var product_pay_term;
			var product_pay_during;
			var product_prem;
			var oldName=calInfos[k].name;
			var array=oldName.split("$");
			var newName="";
			if(array.length==1){
				newName=array[0];
			}else if(array.length==2){
				newName=array[1];
			}
			//保障计划类别
			if(calInfos[k].itf_type=="3"){
				product_type =calInfos[k].description;
			}else{
				product_type ="";
			}
			//险种名称
			product_name =newName;
			//险种代码
			product_code =calInfos[k].code;
			if(calInfos[k].itf_insuyearflag=="A" && Number(calInfos[k].itf_insuyear)<999){
				product_ins_term ="至"+calInfos[k].itf_insuyear+"周岁";
			}else if(calInfos[k].itf_insuyearflag=="Y" && Number(calInfos[k].itf_insuyear)<999){
				product_ins_term =calInfos[k].itf_insuyear+"年";
			}else{
				product_ins_term ="至终身";
			}
			if(calInfos[k].amount=="-"){
				//保险金额
				product_amount ="";
				//交费方式
				product_pay_term ="";
				//交费期间(年或至周岁)
				product_pay_during ="";
				product_prem ="";
				product_ins_term ="同主险";
			}else{
				if(calInfos[k].amount.indexOf("份")>-1){
					product_amount =calInfos[k].itf_share+"份";
				}else{
					product_amount =moneyChange(calInfos[k].itf_amnt)+"元";
				}
				if(calInfos[k].itf_type=="3"){
					product_amount = "";
				}
				if(calInfos[k].itf_payintv==0){
					product_pay_term = "趸交";
					product_pay_during = "一次交清";
				}else{
					product_pay_term ="年交";
					if(calInfos[k].itf_payendyearflag=="A" && Number(calInfos[k].itf_payendyear)<999){
						product_pay_during ="至"+calInfos[k].itf_payendyear+"周岁";
					}else if(calInfos[k].itf_payendyearflag=="Y" && Number(calInfos[k].itf_payendyear)<999){
						product_pay_during =calInfos[k].itf_payendyear+"年";
					}else{
						product_pay_during ="至终身";
					}	
				}
				if((calInfos[k].itf_prem.toString())==""){
					product_prem =calInfos[k].itf_prem;
				}else{
					//获取388浮动费率FloatRate标识（1为正常不处理、0.7则需加上（限本年））
					var codeSubStr = calInfos[k].code.substring(2,5);
					if(codeSubStr=='388'){
						if(calInfos[k].itf_flow_rate=='0.7'){
							product_prem =moneyChange(calInfos[k].itf_prem)+"元(限本年)";
						}else{
							product_prem =moneyChange(calInfos[k].itf_prem)+"元";
						}
					}else{
						product_prem =moneyChange(calInfos[k].itf_prem)+"元";
					}
				}
			}
			calInfo_tr.append("<tr><td>"+product_name+"</td>"+
			"<td>"+product_code+"</td><td>"+product_type+"</td>"+
			"<td>"+product_ins_term+"</td>"+
			"<td>"+product_amount+"</td>"+
			"<td>"+product_pay_term+"</td>"+
			"<td>"+product_pay_during+"</td>"+
			"<td>"+product_prem+"</td></tr>");
		}
	}
	//话术内容显示
	var salemanOption =getSalemanOption("key");
	var dateVal,dateText;
	for (var i = 0; i<salemanOption.length;i++) {
		if(salemanOption[i].code == "para_policyEffectiveDate"){
			dateVal=salemanOption[i].val;
		}else if(salemanOption[i].code == "para_policyEffectiveDate_text"){
			dateText= salemanOption[i].val;
		}
	}
	if(dateVal == "1"){
		calInfo_tr.append("<tr><td colspan='8' class='t_left bold'>"+dateText +"</td></tr>");
	}
	//获取试算保费
	td_prem = moneyChange(getCalPrem("key"));
	var td_capital_prem = rmbCapital(td_prem);
	//是否同意续保	
	var is_agree="";
	if(judgeRenewal()!=null){
		is_agree =judgeRenewal();
	}
	if(is_agree == "是" || is_agree == "否"){
		calInfo_tr.append("<tr><td colspan='6' class='t_left'>保险费合计：（大写）"+td_capital_prem +"&nbsp;&nbsp;（小写）￥："+td_prem+"元</td><td colspan='2'  class='t_left'>如您投保可自动续保产品，<br />是否同意自动续保："+is_agree+"</td></tr><tr><td colspan='8' id='policy_info'></td></tr>");
	}else{
		calInfo_tr.append("<tr><td colspan='8' class='t_left'>保险费合计：（大写）"+td_capital_prem +"&nbsp;&nbsp;（小写）￥："+td_prem+"元</td></tr><tr><td colspan='8' id='policy_info'></td></tr>");
	}
	//首期、续期 2016年7月29日 16:46:30 苏建东
	var policy= getPolicy("key");
	$("#policy_info").html("<table><tr><td>首期</td><td colspan='4' class='t_left' width='50px'>"+
		"<span>交费形式："+policy.new_pay_mode_name+"</span><span>指定账户姓名："+policy.new_acc_name+"</span><span>开户行："+policy.new_bank_name +"</span><span>账号："+policy.new_bank_accno+"</span></td></tr></table>");
	if(policy.pay_mode_name !=""){
		$("#policy_info table").append("<tr><td>续期</td><td colspan='4' class='t_left'>"+
		"<span>交费形式："+policy.pay_mode_name+"</span><span>指定账户姓名："+policy.acc_name+"</span><span>开户行："+ policy.back_name+"</span><span>账号："+policy.bank_accno+"</span></td></tr>");
	}else{
		$("#policy_info table").append("<tr><td>续期</td><td colspan='4' class='t_left'>"+
		"<span>交费形式：</span><span>指定账户姓名：</span><span>开户行：</span><span>账号：</span></td></tr>");
	}
	/*
	 * 领取信息
	 * 苏建东
	 * 2016年8月18日
	 * 
	 */
	getintv = getFrequency(getintv,mainCode);
	if (getyear!=""||getintv!=""||getendperiod!="" ||getkind!=""||bonusMode()!=null) {
		//领取红利方式 2016年8月1日 10:39:06 苏建东
		if(getyear!=""){
			getyear = getyear+"周岁";
		}
		if(bonusMode()!=null){
			$("#policy_info table").append("</tr><tr><td>领取信息</td><td colspan='4' class='t_left bonus_frequency''>"+
				"<span>领取年龄："+getyear+"</span><span>领取频率："+getintv+"</span><span>领取期限："+getendperiod+"</span>红利领取方式："+bonusMode() +
				"<h6 style='font-weight: bold;color: #000;'>提示：若投保险种无约定领取，不涉及上述领取信息。</h6></td></tr>");
		}else{
			$("#policy_info table").append("</tr><tr><td>领取信息</td><td colspan='4' class='t_left frequency'>"+
				"<span>领取年龄："+getyear+"</span><span>领取频率："+getintv+"</span><span>领取期限："+getendperiod+"</span><span>领取方式："+getKindMode(getkind)+"</span>"+
				"<h6 style='font-weight: bold;color: #000;'>提示：若投保险种无约定领取，不涉及上述领取信息。</h6></td></tr>");
		}
	}
}
function rmbCapital(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
        return "数据非法";
    var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p+1, 2);
        unit = unit.substr(unit.length - n.length);
    for (var i=0; i < n.length; i++)
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}
//特别申请
function showSpecialApplication(){
	var intelligentInsure=getIntelligentInsure("case");
	var calInfos=intelligentInsure.benefits.content;
	var additionalVal ="";
	var main_insurance;
	var special_insurance;
	var additional_array;
	var insuranceArray=[];
	for(var k=0;k<calInfos.length;k++){
		if((calInfos[k].itf_share>0 || calInfos[k].itf_prem>0) && (calInfos[k].itf_type=="1" || calInfos[k].itf_type=="2" || calInfos[k].itf_type=="3")){
			//保险
			if(calInfos[k].code != "00717001" && calInfos[k].code != "00718001" && calInfos[k].code != "00563100"){
				insuranceArray.push(calInfos[k].name);
			}
			//特殊险
			if(calInfos[k].code == "00717001" || calInfos[k].code == "00718001" || calInfos[k].code == "00563100"){
				special_insurance = calInfos[k].name;
				var special_array = special_insurance.split("$");
				if(special_array.length ==1){
					special_insurance = special_array[0];
				}else if(special_array.length ==2){
					special_insurance = special_array[1];
				}
				$(".special_insurance").append("“"+special_insurance+"”、");
				$("#special_application").show();
			}
		}
	}
	//主险
	var main_array = insuranceArray[0].split("$");
	if(main_array.length==1){
		main_insurance = main_array[0];
	}else if(main_array.length==2){
		main_insurance = main_array[1];
	}
	$(".main_insurance").html("“"+main_insurance+"”");
	//附加险
	var additional_array = insuranceArray.slice(1,insuranceArray.length);
	if (additional_array =="") {
		$("#special_application td >p:last-child").hide();
	}
	for (var i = 0;i < additional_array.length;i++) {
		additional_insurance = additional_array[i];
		var additional = additional_insurance.split("$");
		if(additional.length==1){
			additional_insurance = additional[0];
		}else if(additional.length==2){
			additional_insurance = additional[1];
		}
		$(".additional_insurance").append("“"+additional_insurance+"”、");
	}
	specialVal = $(".special_insurance").text();
	$(".special_insurance").html(specialVal.substr(0,specialVal.length-1));
	additionalVal = $(".additional_insurance").text();
	$(".additional_insurance").html(additionalVal.substr(0,additionalVal.length-1));
}
//显示含有智能核保的告知信息
function showImpartHasIntell(){
	var intelligentInsure=getIntelligentInsure("case");
	var insureQuestionAndResult=intelligentInsure.case_xml;
	//投保人
	var holderQuestionAndResult=intelligentInsure.case_holder_xml;
	var impart=[];
	if(insureQuestionAndResult!=""){
		var insure_json=eval("("+insureQuestionAndResult+")");
		var insureImpart=new Object();
		insureImpart.flags="insure";
		insureImpart.content=insure_json;
		impart.push(insureImpart);
	}
	if(holderQuestionAndResult!=""){
		var holder_json=eval("("+holderQuestionAndResult+")");
		var holderImpart=new Object();
		holderImpart.flags="holder";
		holderImpart.content=holder_json;
		impart.push(holderImpart);
	}
	if(impart.length<1)return;
	
	var impart_intell_table= document.createElement('table');
	var tr_title = document.createElement('tr');
	var td_title = document.createElement('td')
	td_title.innerText="投保告知";
	td_title.setAttribute("colspan",2);
	$(td_title).css("color","#000");
	$(td_title).css("font-weight", "700");
	td_title.setAttribute("align","center");
	tr_title.appendChild(td_title);
	impart_intell_table.appendChild(tr_title);
	
	for(var i=0;i<impart.length;i++){
		var flags=impart[i].flags;
		var content=impart[i].content;
		var tr_name = document.createElement('tr');
		var td_name = document.createElement('td');
		if(flags=="insure"){
			var insureName="";
			var insureOption=getInsureOption("key",0);
			for(var a=0;a<insureOption.length;a++){
				if(insureOption[a].code=="name"){
					insureName=insureOption[a].val_text; 
				}
			}
			
			td_name.innerText="被保险人:"+insureName;
		}else if(flags=="holder"){
			var holderName="";
			var holderOption=getHolderOption("key");
			for(var a=0;a<holderOption.length;a++){
				if(holderOption[a].code=="name"){
					holderName=holderOption[a].val_text; 
				}
			}
			td_name.innerText="投保人:"+holderName;
		}
		$(td_name).css("color","#000");
		tr_name.appendChild(td_name);
		td_name.setAttribute("colspan",2);
		impart_intell_table.appendChild(tr_name);
		
		for(var j=0;j<content.length;j++){
			var impart_intell_tr = document.createElement('tr');
			var impart_intell_tr2 = document.createElement('tr');
			var td_question = document.createElement('td');
			var td_answer = document.createElement('td');
//			td_answer.setAttribute("width",120);
//			td_answer.setAttribute("align","center");
			var index=j+1;
			td_question.innerText=index+"、"+content[j].QText;
			td_answer.innerText= "答案:"+content[j].Answer;
			impart_intell_tr.appendChild(td_question);
			impart_intell_tr2.appendChild(td_answer);
			impart_intell_table.appendChild(impart_intell_tr);
			impart_intell_table.appendChild(impart_intell_tr2);
		}
		document.getElementById("finance_introduce").appendChild(impart_intell_table);
	}
}
function moneyChange(money) {
	var moneyArray = money.toString().split(".");
	if (moneyArray.length == 1) {
		money += ".00";
	} else if (moneyArray.length == 2) {
		var integerMoney = moneyArray[0];
		var decimalMoney = moneyArray[1];
		var len = decimalMoney.length;
		if (len == 1) {
			money = integerMoney + "." + decimalMoney + "0";
		}else if(len==2){
			money = integerMoney + "." + decimalMoney;
		}else if(len>2){
			money=money+0.005;
			var moneyStr=money.toString();
			var deIndex=moneyStr.indexOf(".");
			money=moneyStr.substr(0,deIndex+3);
		}
	}
	return money;
}

function init(){
	var cal_para=getLocalObj("cal_para");
	if(cal_para!=null){
		for(var k=0;k<cal_para.length;k++){
			if(cal_para[k].code=="para_6521_flag"){
				var result=$("#customerStatement").html();
			    result+="<p>8、人作为该单的投保人/被保险人，均同意将该投保单主险尊享人生保险期间届满前项下的生存类保险金，";
				result+="自动转为该投保单《附加随意领年金保险（万能型）》合同的保险费，不再转入主险累积生息账户。";
				result+="上述申请为本人自愿，由此引起的所有法律和经济责任均由本人承担，与新华人寿保险股份有限公司无关。";
				result+=" 注：生存类保险金指主险合同约定以被保险人生存为给付条件的保险金（具体释义详见《附加随意领年金保险（万能型）》条款），";
				result+="包括并不限于关爱金、关爱年金、生存保险金、祝寿金及婚姻祝福金（具体视主险责任确定）。</p>";
				$("#customerStatement").html(result);
			}
		}
	}
}			
			
function judgeRenewal(){
	var renewResult=null;
	var cal_para= getLocalObj("cal_para");
	if(cal_para==null)return;
	for(var k=0;k<cal_para.length;k++){
		if(cal_para[k].code=="para_renew_flag"){
			if(cal_para[k].val==1){
				renewResult="是";
			}else if(cal_para[k].val==0){
				renewResult="否";
			}
		}
	}
	return renewResult;
}
//红利领取方式
function bonusMode(){
	var cal_para= getLocalObj("cal_para");
	if(cal_para==null)return;
	for(var k=0;k<cal_para.length;k++){
		if(cal_para[k].code=="para_bonus_msg"){
			if(cal_para[k].val[0]==3){
				return cal_para[k].val[1];
			}
		}
	}
	return null;
}
//收入
function getYearIncome(val){
	switch (val) {
		case "1":
			return "3.5万以下";
		case "2":
			return "3.5万-6万";
		case "3":
			return "6万-10万";
		case "4":
			return "10万至30万";
		case "5":
			return "30万以上";
		default:
			return "";
	}
}
//支付方式
function getNewBankName(val){
	switch (val) {
		case "1":
			return "转账支付";
		case "0":
			return "刷卡支付";
		case "2":
			return "批次转账";
		case "5":
			return "--";
		default:
			return "";
	}
}
//支付方式
function getOldBankName(val){
	switch (val) {
		case "0":
			return "银行转账";
		case "1":
			return "自行交纳";
		case "2":
			return "上门收费";
		case "5":
			return "--";
		default:
			return "";
	}
}
//收入来源
function getIncomeSource(val){
	var SourceResult=[];
	for(var i=1;i<val.length;i++){
		switch (val[i]) {
		case "1":
			SourceResult.push("工薪");
			break;
		case "2":
			SourceResult.push("个体");
			break;
		case "3":
			SourceResult.push("私营");
			break;
		case "4":
			SourceResult.push("房屋出租");
			break;
		case "5":
			SourceResult.push("证券投资");
			break;
		case "6":
			SourceResult.push("银行利息");
			break;
		case "7":
			SourceResult.push("其他");
			break;
		default:
			break;
		}
	}
	return SourceResult;
}
//居民类型
function getResidentType(val){
	switch (val) {
		case "01":
			return "城镇居民";
		case "02":
			return "农村居民";
		default:
			return "";
	}
}

/*
 * 领取方式
 * 苏建东
 * 2016年8月18日
 */
function getKindMode(val){
	switch (val) {
		case 1:
			return "平准型";
		case 2:
			return "递增型";
		default:
			return "";
	}
}
/*
 * 领取频率
 * 苏建东
 * 2016年8月19日
 */
function getFrequency(month,mianCode){
	if(mianCode == "00671000") {
		if(month != null) {
			return(month == "1" ? "月领" : (month == "12") ? "年领" :"");
		} else {
			return "";
		}
	} else {
		if(month != null) {
			return(month == "1" ? "年领" : (month == "2") ? "月领" :"");
		} else {
			return "";
		}
	}
}
/**
 * 2016年12月10日
 * 苏建东
 * 显示保险公司信息
 */
function showSalemaninfos(saleman){
	for (var k = 0; k < saleman.length; k++) {
	var saleman_code = saleman[k].code;
	var saleman_val = saleman[k].val;
	switch (saleman_code) {
		case "agentName":
			$("#yewuyuanXX").append(saleman_val);
			break;
		case "id":
			$("#yewuyuanH").append(saleman_val);
			break;
		case "comName":
			$("#shjg").append(saleman_val);
			break;
		case "agentMobile":
			$("#agentMobile").append(saleman_val);
			break;
		case "managerCode":
			$("#managerCode").append(saleman_val);
			break;
		case "agentBankCode":
			$("#agentBankCode").append(saleman_val);
			break;
		case "agentCom":
			$("#agentCom").append(saleman_val);
			break;
		default:
			break;
		}
	}
}