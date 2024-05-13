package br.com.erudio.restprojecterudio.mapper;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <T, D> D parseObject(T origin, Class<D> destination){
        return mapper.map(origin, destination);
    }
    public static <T, D> List<D> parseListObjects(List<T> origin, Class<D> destination){
        List<D> destinationObjects = new ArrayList<D>();
        for (T o: origin){
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }

}
