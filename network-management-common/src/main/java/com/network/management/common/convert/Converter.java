package com.network.management.common.convert;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * object convert interface
 * @author yyc
 * @date 2020/9/13 13:25
 */
public interface Converter<S, T> {
    T convert(S s);

    S reverseConvert(T s);

    default List<T> convertToList(Collection<S> sourceList) {
        return sourceList == null ? null : sourceList.stream().filter(Objects::nonNull)
                .map(this::convert).filter(Objects::nonNull).collect(toList());
    }

    default List<S> reverseConvertToList(Collection<T> targetList) {
        return targetList == null ? null : targetList.stream().filter(Objects::nonNull)
                .map(this::reverseConvert).filter(Objects::nonNull).collect(toList());
    }
}
