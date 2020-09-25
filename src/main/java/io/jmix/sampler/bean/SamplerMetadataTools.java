package io.jmix.sampler.bean;

import io.jmix.core.Entity;
import io.jmix.core.MetadataTools;
import io.jmix.core.entity.annotation.IgnoreUserTimeZone;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.datatype.TimeZoneAwareDatatype;
import io.jmix.core.metamodel.model.MetaProperty;
import io.jmix.core.metamodel.model.Range;
import io.jmix.ui.App;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.stream.Collectors;

import static io.jmix.core.common.util.Preconditions.checkNotNullArgument;

public class SamplerMetadataTools extends MetadataTools {

    @Override
    public String format(@Nullable Object value, MetaProperty property) {
        checkNotNullArgument(property, "property is null");

        if (value == null) {
            return "";
        }

        Range range = property.getRange();
        // todo dynamic attributes
//        if (DynamicAttributesUtils.isDynamicAttribute(property)) {
//            CategoryAttribute categoryAttribute = DynamicAttributesUtils.getCategoryAttribute(property);
//
//            if (categoryAttribute.getDataType().equals(PropertyType.ENUMERATION)) {
//                return LocaleHelper.getEnumLocalizedValue((String) value, categoryAttribute.getEnumerationLocales());
//            }
//
//            if (categoryAttribute.getIsCollection() && value instanceof Collection) {
//                return dynamicAttributesTools.getDynamicAttributeValueAsString(property, value);
//            }
//        }

        if (range.isDatatype()) {
            Datatype datatype = range.asDatatype();
            if (datatype instanceof TimeZoneAwareDatatype) {
                Boolean ignoreUserTimeZone = getMetaAnnotationValue(property, IgnoreUserTimeZone.class);
                if (!Boolean.TRUE.equals(ignoreUserTimeZone)) {
                    return ((TimeZoneAwareDatatype) datatype).format(value,
                            App.getInstance().getLocale(), currentAuthentication.getTimeZone());
                }
            }
            return datatype.format(value, App.getInstance().getLocale());
        } else if (range.isEnum()) {
            return messages.getMessage((Enum) value);
        } else if (value instanceof Entity) {
            return getInstanceName(value);
        } else if (value instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<Object> collection = (Collection<Object>) value;
            return collection.stream()
                    .map(this::format)
                    .collect(Collectors.joining(", "));
        } else {
            return value.toString();
        }
    }

    @Override
    public String format(@Nullable Object value) {
        if (value == null) {
            return "";
        } else if (value instanceof Entity) {
            return getInstanceName(value);
        } else if (value instanceof Enum) {
            return messages.getMessage((Enum) value, App.getInstance().getLocale());
        } else if (value instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<Object> collection = (Collection<Object>) value;
            return collection.stream()
                    .map(this::format)
                    .collect(Collectors.joining(", "));
        } else {
            Datatype datatype = datatypeRegistry.find(value.getClass());
            if (datatype != null) {
                return datatype.format(value, App.getInstance().getLocale());
            }

            return value.toString();
        }
    }
}
