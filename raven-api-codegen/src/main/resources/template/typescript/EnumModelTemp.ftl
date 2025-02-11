/**
 * ${description!}
 */
declare enum ${className} {
	<#list enums as enum>

	/**
	* ${enum.description!}
	*/
	${enum.name} = <#if enumIsString == true>"${enum.value}"<#else><#if enumTypeConvert?? && enumTypeConvert != "">${enumTypeConvert}("${enum.value}")<#else>${enum.value}</#if></#if>,
	</#list>

}


