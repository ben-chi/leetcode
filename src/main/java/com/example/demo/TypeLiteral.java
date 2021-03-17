package com.example.demo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TypeLiteral<T> {
    private Type type;
    public TypeLiteral(){
        Type parentType=getClass().getGenericSuperclass();
        if (parentType instanceof ParameterizedType){
            type=((ParameterizedType) parentType).getActualTypeArguments()[0];
        }
        else
            throw new UnsupportedOperationException(
                    "Construct as new TypeLiteral<...>(){} "
            );
    }
    private TypeLiteral(Type type){
        this.type=type;
    }
    public static TypeLiteral<?> of(Type type){
        return  new TypeLiteral<Object>(type);
    }
    public String toString(){
        if(type instanceof Class) return ((Class<?>) type).getName();
        else return type.toString();
    }
    public boolean equals(Object otherObject){
        return  otherObject instanceof TypeLiteral &&
                type.equals(((TypeLiteral<?>) otherObject).type);
    }
    public int hashCode(){
        return type.hashCode();
    }

}
class Formatter{
    private Map<TypeLiteral<?>, Function<?,String>> rules=new HashMap<>();
    public <T> void forType(TypeLiteral<T> type,Function<T,String> formatterForType){
        rules.put(type,formatterForType);
    }
    public String formatFields(Object obj) throws IllegalAccessException {
        var result=new StringBuilder();
        for(Field f:obj.getClass().getDeclaredFields())
        {
            result.append(f.getName());
            result.append("=");
            f.setAccessible(true);
            Function<?,String> formatterForType=rules.get(TypeLiteral.of(f.getGenericType()));
            if(formatterForType!=null){
                Function<Object,String> objectFormatter
                        =(Function<Object, String>) formatterForType;
                result.append(objectFormatter.apply(f.get(obj)));
            }
            else
                result.append(f.get(obj).toString());
            result.append("\n");
        }
        return result.toString();
    }
}

