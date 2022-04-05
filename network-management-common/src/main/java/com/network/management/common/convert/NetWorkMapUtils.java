package com.network.management.common.convert;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 流式map工具类
 * @author yyc
 * @date 2021/9/9 17:09
 */
public class NetWorkMapUtils {

    /**
     * List转成Map
     * @param data      请求数据
     * @param keyGetter 获取Key
     * @param <T>       类型
     * @param <P>       参数
     * @return 结果
     */
    public static <T, P> Map<T, P> toMap(Collection<P> data, Function<P, T> keyGetter) {
        // 转成目标数据
        return CollectionUtils.emptyIfNull(data).stream()
                // 过滤不合法数据
                .filter(each -> Objects.nonNull(each) && Objects.nonNull(keyGetter.apply(each)))
                // 收集结果
                .collect(Collectors.toMap(keyGetter, Function.identity(), (o, n) -> n));
    }

    /**
     * Map转为Group
     *
     * @param data      请求数据
     * @param keyGetter Key生成器
     * @param <T>       参数
     * @param <P>       类型
     * @return 结果
     */
    public static <T, P> Map<T, List<P>> toGroup(Collection<P> data, Function<P, T> keyGetter) {
        // 转为Map
        return CollectionUtils.emptyIfNull(data)
                .stream()
                .filter(each -> Objects.nonNull(each) && Objects.nonNull(keyGetter.apply(each)))
                .collect(Collectors.groupingBy(keyGetter));
    }

    /**
     * 去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> filterDistinctKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 计算集合数量
     * @param data 集合列表
     * @param keyGetter getter
     * @param identity 默认值
     * @param accumulator 计算方式
     * @param <T> 返回值
     * @param <P> 集合参数类型
     * @return 计算集合数量
     */
    public static <T, P> T calculateTotalNumber(Collection<P> data, Function<P, T> keyGetter, T identity, BinaryOperator<T> accumulator){
        return CollectionUtils.emptyIfNull(data)
                .stream()
                .filter(each -> Objects.nonNull(each) && Objects.nonNull(keyGetter.apply(each)))
                .map(keyGetter)
                .reduce(identity, accumulator);
    }

    /**
     * 根据条件过滤数据
     * @param data 集合列表
     * @param predicate 判断条件
     * @param <P> 集合参数类型
     * @return 过滤后的集合列表
     */
    public static <P> List<P> toList(Collection<P> data, Predicate<? super P> predicate) {
        // 转为Map
        return CollectionUtils.emptyIfNull(data)
                .stream()
                .filter(each -> Objects.nonNull(each) && predicate.test(each))
                .collect(Collectors.toList());
    }

    /**
     * 集合装换为其他类型的集合
     *
     * @param data      请求数据
     * @param keyGetter Key生成器
     * @param <T>       参数
     * @param <P>       类型
     * @return 结果
     */
    public static <T, P> Set<T> toSet(Collection<P> data, Function<P, T> keyGetter) {
        // 转为Set
        return CollectionUtils.emptyIfNull(data)
                .stream()
                .filter(each -> Objects.nonNull(each) && Objects.nonNull(keyGetter.apply(each)) )
                .map(keyGetter)
                .collect(Collectors.toSet());
    }

    /**
     * 获取新集合列表
     * @param data 集合列表
     * @param keyGetter Key生成器
     * @return 新集合列表
     */
    public static <T, P> List<T> toOtherList(Collection<P> data, Function<P, T> keyGetter) {
        // 转为Map
        return CollectionUtils.emptyIfNull(data)
                .stream()
                .filter(Objects::nonNull)
                .map(keyGetter)
                .collect(Collectors.toList());
    }

    /**
     * List转Map(去重)
     * @param data      请求数据
     * @param keyGetter 获取Key
     * @param <T>       类型
     * @param <P>       参数
     * @return 结果
     */
    public static <T, P> Map<T, P> toFilterMap(Collection<P> data, Function<P, T> keyGetter) {
        // 转成目标数据
        return CollectionUtils.emptyIfNull(data).stream()
                // 过滤不合法数据
                .filter(each -> Objects.nonNull(each) && Objects.nonNull(keyGetter.apply(each)))
                .filter(filterDistinctKey(keyGetter))
                // 收集结果
                .collect(Collectors.toMap(keyGetter, Function.identity(), (o, n) -> n));
    }

}
