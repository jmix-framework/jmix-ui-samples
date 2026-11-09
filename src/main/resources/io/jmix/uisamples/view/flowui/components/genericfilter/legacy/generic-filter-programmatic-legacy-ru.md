В этом примере показана настройка `GenericFilter` в Java без builder API: компонент, его конфигурация и компонент фильтра создаются и связываются вручную (`uiComponents.create(...)`, `PropertyConditionUtils.generateParameterName(...)`, `SingleFilterSupport.generateValueComponent(...)`, `DesignTimeConfiguration`).

Сравните с примером [GenericFilter builder: обзор]({contextPath}/sample/generic-filter-programmatically-defined), где то же самое сделано через builder API.
