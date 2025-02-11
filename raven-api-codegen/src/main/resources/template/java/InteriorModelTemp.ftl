package ${packageName};

<#list imports as import>
import ${import};
</#list>
import javax.annotation.*;

/**
 * ${description!}
 */
public class ${className}<#if genericTypeName??>${genericTypeName}</#if><#if extend??> extends ${extend}</#if> {
<#list members as member>

	/**
	 * ${member.description!}
	 */
	<#if member.constraint?? && member.constraint.nullable>
	@Nullable
	</#if>
	private ${member.type} ${member.filed};
</#list>

//#region field get/set
<#list members as member>

	public ${member.type} get${member.property}() {
		return ${member.filed};
	}

	public void set${member.property}(${member.type} ${member.filed}) {
		this.${member.filed} = ${member.filed};
	}
</#list>

//endregion

	@Override
	public String toString() {
		return "${className}{" +

<#if extend??>
				"super=" + super.toString() + "," +
</#if>
<#list members as member>
				"${member.filed}='" + ${member.filed} + "'" + <#sep>"," +
</#list>

				'}';

	}

}


