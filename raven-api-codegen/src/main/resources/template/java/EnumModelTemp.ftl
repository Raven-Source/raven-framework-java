package ${packageName};

<#list imports as import>
import ${import};
</#list>

/**
 * ${description!}
 */
public class ${className}<#if genericTypeName??>${genericTypeName}</#if><#if extend??> extends ${extend}</#if><#if implement??> implements ${implement}</#if> {

	private final ${enumType} value;

	public ${enumType} getValue() {
		return value;
	}

	private ${className}(${enumType} value) {
		this.value = value;
	}
<#list enums as enum>

	/**
	 * ${enum.description!}
	 */
	public final static ${className} ${enum.name} = new ${className}(<#if enumIsString == true>"${enum.value}"<#elseif enumTypeConvert?? && enumTypeConvert != "">${enumTypeConvert}(${enum.value})<#else>${enum.value}</#if>);
</#list>

	@Values
	public static ${className}[] values() {
		return new ${className}[]{
<#list enums as enum>
			<#if enum?index != 0>,</#if>${enum.name}
</#list>
		};
	}

	@Create
	private static ${className} valueOf(${enumType} value) {
		for (${className} v : values()) {
			if (v.equalsValue(value)) {
				return v;
			}
		}
		return new ${className}(value);
	}

	@Override
	public String toString() {
		return value.toString();
	}

}


