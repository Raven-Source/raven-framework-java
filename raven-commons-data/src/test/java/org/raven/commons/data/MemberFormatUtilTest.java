package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;

public class MemberFormatUtilTest {

    @Test
    public void invoke() {

        Assert.assertEquals(MemberFormatUtil.namingFormat("pascalCase", MemberFormatType.PascalCase), "PascalCase");
        Assert.assertEquals(MemberFormatUtil.namingFormat("PascalCase", MemberFormatType.PascalCase), "PascalCase");

        Assert.assertEquals(MemberFormatUtil.namingFormat("CamelCase", MemberFormatType.CamelCase), "camelCase");

        Assert.assertEquals(MemberFormatUtil.namingFormat("SnakeCase", MemberFormatType.SnakeCase), "snake_case");
        Assert.assertEquals(MemberFormatUtil.namingFormat("snakeCase", MemberFormatType.SnakeCase), "snake_case");
        Assert.assertEquals(MemberFormatUtil.namingFormat("Snake_Case", MemberFormatType.SnakeCase), "snake_case");

        Assert.assertEquals(MemberFormatUtil.namingFormat("KebabCase", MemberFormatType.KebabCase), "kebab-case");
        Assert.assertEquals(MemberFormatUtil.namingFormat("kebabCase", MemberFormatType.KebabCase), "kebab-case");
        Assert.assertEquals(MemberFormatUtil.namingFormat("kebab-Case", MemberFormatType.KebabCase), "kebab-case");

    }

    private String format(MemberFormatType formatType) {
        return MemberFormatUtil.namingFormat(formatType.toString(), formatType);
    }

}
