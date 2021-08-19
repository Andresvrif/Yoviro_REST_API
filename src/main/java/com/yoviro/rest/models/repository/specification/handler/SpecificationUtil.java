package com.yoviro.rest.models.repository.specification.handler;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SpecificationUtil {
    public static <T> Specification<T> bySearchQuery(SearchQuery searchQuery, Class<T> clazz, Boolean applyDistinct) {

        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criterailBuilder) -> {
            query.distinct(applyDistinct);
            List<Predicate> predicates = new ArrayList<>();

            // Add Predicates for tables to be joined
            List<JoinColumnProps> joinColumnProps = searchQuery.getJoinColumnProps();

            if (joinColumnProps != null && !joinColumnProps.isEmpty()) {
                for (JoinColumnProps joinColumnProp : joinColumnProps) {
                    addJoinColumnProps(predicates, joinColumnProp, criterailBuilder, root);
                }
            }

            List<SearchFilter> searchFilters = searchQuery.getSearchFilter();

            if (searchFilters != null && !searchFilters.isEmpty()) {
                for (final SearchFilter searchFilter : searchFilters) {
                    addPredicates(predicates, searchFilter, criterailBuilder, root);
                }
            }

            if (predicates.isEmpty()) {
                return criterailBuilder.conjunction();
            }

            return criterailBuilder.and(predicates.toArray(new Predicate[0]));

        };
    }

    private static <T> void addJoinColumnProps(List<Predicate> predicates,
                                               JoinColumnProps joinColumnProp,
                                               CriteriaBuilder criterailBuilder,
                                               Root<T> root) {

        List<SearchFilter> filters = joinColumnProp.getSearchFilter();
        Join<Object, Object> joinParent = root.join(joinColumnProp.getJoinColumnName());
        for (SearchFilter searchFilter : filters) {
            String property = searchFilter.getProperty();
            Path expression = joinParent.get(property);

            addPredicate(predicates, searchFilter, criterailBuilder, expression);
        }

        //Subjoin
        JoinColumnProps subJoinColumnProps = joinColumnProp.getSubJoinColumnProps();
        if (subJoinColumnProps != null) {
            Join<Object, Object> subJoin = joinParent.join(subJoinColumnProps.getJoinColumnName());
            addSubJoinColumnProps(predicates, subJoinColumnProps, criterailBuilder, subJoin);
        }
    }

    private static <T> void addSubJoinColumnProps(List<Predicate> predicates,
                                                  JoinColumnProps joinColumnProp,
                                                  CriteriaBuilder criterailBuilder,
                                                  Join<Object, Object> joinParent) {
        List<SearchFilter> subFilters = joinColumnProp.getSearchFilter();
        for (SearchFilter subSearchFilter : subFilters) {
            String property = subSearchFilter.getProperty();
            Path expression = joinParent.get(property);

            addPredicate(predicates, subSearchFilter, criterailBuilder, expression);
        }

        //Subjoin
        JoinColumnProps subJoinColumnProps = joinColumnProp.getSubJoinColumnProps();
        if (subJoinColumnProps != null) {
            Join<Object, Object> subJoin = joinParent.join(subJoinColumnProps.getJoinColumnName());
            addSubJoinColumnProps(predicates, subJoinColumnProps, criterailBuilder, subJoin);
        }
    }

    private static <T> void addPredicates(List<Predicate> predicates, SearchFilter searchFilter,
                                          CriteriaBuilder criterailBuilder, Root<T> root) {
        String property = searchFilter.getProperty();
        Path expression = root.get(property);

        addPredicate(predicates, searchFilter, criterailBuilder, expression);

    }

    private static void addPredicate(List<Predicate> predicates, SearchFilter searchFilter,
                                     CriteriaBuilder criterailBuilder, Path expression) {
        switch (searchFilter.getOperator()) {
            case EQUALS:
                predicates.add(criterailBuilder.equal(expression, searchFilter.getValue()));
                break;
            case LIKE:
                predicates.add(criterailBuilder.like(expression, "%" + searchFilter.getValue() + "%"));
                break;
            case IN:
                predicates.add(criterailBuilder.in(expression).value(searchFilter.getValue()));
                break;
            case GREATER_THAN:
                predicates.add(criterailBuilder.greaterThan(expression, (Comparable) searchFilter.getValue()));
                break;
            case LESS_THAN:
                predicates.add(criterailBuilder.lessThan(expression, (Comparable) searchFilter.getValue()));
                break;
            case GREATER_THAN_OR_EQUAL:
                predicates.add(criterailBuilder.greaterThanOrEqualTo(expression, (Comparable) searchFilter.getValue()));
                break;
            case LESS_THAN_OR_EQUAL:
                predicates.add(criterailBuilder.lessThanOrEqualTo(expression, (Comparable) searchFilter.getValue()));
                break;
            case NOT_EQUALS:
                predicates.add(criterailBuilder.notEqual(expression, searchFilter.getValue()));
                break;
            case IS_NULL:
                predicates.add(criterailBuilder.isNull(expression));
                break;
            case NOT_NULL:
                predicates.add(criterailBuilder.isNotNull(expression));
                break;
            default:
                System.out.println("Predicate is not matched");
                throw new IllegalArgumentException(searchFilter.getOperator() + " is not a valid predicate");
        }

    }
}
