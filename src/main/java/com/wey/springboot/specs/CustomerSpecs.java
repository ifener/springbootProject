package com.wey.springboot.specs;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class CustomerSpecs {
    
    @SuppressWarnings("serial")
    public static <T> Specification<T> byAuto(final EntityManager entityManager, final T example) {
        @SuppressWarnings("unchecked")
        final Class<T> type = (Class<T>) example.getClass();
        return new Specification<T>() {
            
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                EntityType<T> entity = entityManager.getMetamodel().entity(type);
                for (Attribute<T, ?> attr : entity.getDeclaredAttributes()) {
                    Object attrValue = getValue(example, attr);
                    if (attrValue != null) {
                        if (attr.getJavaType() == String.class) {
                            if (!StringUtils.isEmpty(attrValue)) {
                                predicates.add(criteriaBuilder
                                        .like(root.get(attribute(entity, attr.getName(), String.class)),
                                              pattern(attrValue.toString())));
                            }
                        }
                        else {
                            predicates.add(criteriaBuilder
                                    .equal(root.get(attribute(entity, attr.getName(), attr.getClass())), attrValue));
                        }
                    }
                }
                return predicates.isEmpty() ? criteriaBuilder.conjunction()
                                            : criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
            
            private <T> Object getValue(T example, Attribute<T, ?> attr) {
                return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
            }
            
            private <T, E> SingularAttribute<T, E> attribute(EntityType<T> entity, String fieldName,
                    Class<E> fieldClass) {
                return entity.getDeclaredSingularAttribute(fieldName, fieldClass);
            }
            
            private String pattern(String str) {
                return "%" + str + "%";
            }
            
        };
    }
}
