package com.own.garbage.common.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName OperatorEnum
 * @Description
 * @Author liugs
 * @Date 2023/7/5 0005 17:30
 */
public enum OperatorEnum {
    /**
     * Match operator enum.
     */
    MATCH("match", true),

    /**
     * Eq operator enum.
     */
    EQ("=", true),

    /**
     * Regex operator enum.
     */
    REGEX("regex", true),

    /**
     * Gt operator enum.
     */
    GT(">", false),

    /**
     * Lt operator enum.
     */
    LT("<", false),

    /**
     * Contains operator enum.
     */
    CONTAINS("contains", true),

    /**
     * Starts with operator enum.
     */
    STARTS_WITH("startsWith", true),

    /**
     * Ends with operator enum.
     */
    ENDS_WITH("endsWith", true),

    /**
     * Time before operator enum.
     */
    TIME_BEFORE("TimeBefore", true),

    /**
     * Exclude operator enum.
     */
    EXCLUDE("exclude", true),

    /**
     * Time after operator enum.
     */
    TIME_AFTER("TimeAfter", true);

    private final String alias;

    private final Boolean support;

    /**
     * all args constructor.
     *
     * @param alias   alias
     * @param support support
     */
    OperatorEnum(final String alias, final Boolean support) {
        this.alias = alias;
        this.support = support;
    }

    /**
     * get alias.
     *
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * get support.
     *
     * @return support
     */
    public Boolean getSupport() {
        return support;
    }

    /**
     * acquire operator supports.
     *
     * @return operator support.
     */
    public static List<OperatorEnum> acquireSupport() {
        return Arrays.stream(OperatorEnum.values()).filter(e -> e.support).collect(Collectors.toList());
    }

    /**
     * get operator enum by alias.
     *
     * @param alias operator alias.
     * @return operator enum.
     */
    public static OperatorEnum getOperatorEnumByAlias(final String alias) {
        return Arrays.stream(OperatorEnum.values())
                .filter(e -> e.getAlias().equals(alias) && e.support).findFirst()
                .orElse(null);
    }
}
