import { http } from "@/utils/http"

<#--<#list imports as import>-->
<#--import ${import};-->
<#--</#list>-->

<#list operations as operation>

/**
* ${operation.description!}
*/
export async function ${operation.name}(<#list operation.params as param><#if param?index == 0>body: ${param.type}</#if></#list>) {
	return http.request<${operation.resultType}>(
		<#list operation.methods as method><#if method?index == 0>"${method}"</#if></#list>
		<#list operation.paths as path><#if path?index == 0>, "${path}"</#if></#list>
		<#list operation.params as param><#if param?index == 0>, body</#if></#list>
	)
}
</#list>
