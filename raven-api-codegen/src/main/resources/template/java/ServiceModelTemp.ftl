package ${packageName};

<#list imports as import>
import ${import};
</#list>

import javax.annotation.*;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * ${description!}
 */
public interface ${className} {
<#list operations as operation>

	/**
	 * ${operation.description!}
	 */
	@RequestMapping(value = {<#list operation.paths as path><#if path?index != 0>, </#if>"${path}"</#list>}, method = {<#list operation.methods as method><#if method?index != 0>, </#if>${method}</#list>})
	${operation.resultType} ${operation.name}(<#list operation.params as param><#if param?index != 0>, </#if>${param.type} ${param.name}</#list>);
</#list>

}


