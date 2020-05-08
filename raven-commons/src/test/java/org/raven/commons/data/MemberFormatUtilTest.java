package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;

public class MemberFormatUtilTest {

    @Test
    public void invoke() {

        Assert.assertEquals(MemberFormatUtils.namingFormat("pascalCase", MemberFormatType.PascalCase), "PascalCase");
        Assert.assertEquals(MemberFormatUtils.namingFormat("PascalCase", MemberFormatType.PascalCase), "PascalCase");

        Assert.assertEquals(MemberFormatUtils.namingFormat("CamelCase", MemberFormatType.CamelCase), "camelCase");

        Assert.assertEquals(MemberFormatUtils.namingFormat("SnakeCase", MemberFormatType.SnakeCase), "snake_case");
        Assert.assertEquals(MemberFormatUtils.namingFormat("snakeCase", MemberFormatType.SnakeCase), "snake_case");
        Assert.assertEquals(MemberFormatUtils.namingFormat("Snake_Case", MemberFormatType.SnakeCase), "snake_case");

        Assert.assertEquals(MemberFormatUtils.namingFormat("KebabCase", MemberFormatType.KebabCase), "kebab-case");
        Assert.assertEquals(MemberFormatUtils.namingFormat("kebabCase", MemberFormatType.KebabCase), "kebab-case");
        Assert.assertEquals(MemberFormatUtils.namingFormat("kebab-Case", MemberFormatType.KebabCase), "kebab-case");

    }

}
