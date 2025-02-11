<#--// @ts-ignore-->
<#--/* eslint-disable */-->
<#--<#list imports as import>-->
<#--import ${import};-->
<#--</#list>-->

/**
 * ${description!}
 */
declare type ${className}<#if genericTypeName??>${genericTypeName}</#if> =<#if extend??> ${extend} &</#if> {
<#list members as member>

	/**
	 * ${member.description!}
	 */
	${member.filed}<#if member.constraint?? && member.constraint.nullable>?</#if>: ${member.type};
</#list>

}


