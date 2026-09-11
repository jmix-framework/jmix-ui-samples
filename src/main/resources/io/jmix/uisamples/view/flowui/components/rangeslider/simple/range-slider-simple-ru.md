`RangeSlider` позволяет выбрать диапазон значений двумя ползунками: `integerRangeSlider` для границ типа `Integer` и `decimalRangeSlider` для `Double`. Начальный диапазон задаётся атрибутами `startValue` и `endValue`, сама дорожка – атрибутами `min`, `max` и `step`.

Значение компонента – запись вида `IntegerRangeSliderValue` или `DecimalRangeSliderValue` с методами `start()` и `end()`. В отличие от обычного слайдера, диапазонный нельзя связать с контейнером данных.
