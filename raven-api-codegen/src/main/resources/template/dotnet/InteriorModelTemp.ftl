using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

<#list imports as import>
using ${import};
</#list>

namespace ${packageName}
{

    public class ${className} <#if extend??> : ${extend}</#if>
    {

<#list members as member>

        public ${member.type} ${member.property} { get; set; }

</#list>

    }

}
