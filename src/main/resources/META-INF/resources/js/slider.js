import 'jquery/jquery.js'
import 'jquery-ui/dist/jquery-ui.js'
import {LitElement} from 'lit';
import {ElementMixin} from '@vaadin/component-base/src/element-mixin.js';
import {defineCustomElement} from '@vaadin/component-base/src/define.js';
import {PolylitMixin} from '@vaadin/component-base/src/polylit-mixin.js';

export class Slider extends ElementMixin(PolylitMixin(LitElement)) {

    static get is() {
        return 'demo-slider';
    }

    static get properties() {
        return {
            min: {
                type: Number,
                value: 0,
                observer: '_onMinChange'
            },

            max: {
                type: Number,
                value: 100,
                observer: '_onMaxChange'
            },

            value: {
                type: Number,
                notify: true,
                observer: '_onValueChange'
            },

            /** @private */
            _slider: {
                type: Object
            }
        }
    }

    // to disable shadow dom
    createRenderRoot() {
        return this;
    }

    ready() {
        super.ready();

        let $jQuery = jQuery.noConflict();
        this._slider = $jQuery(this);
        this._slider.slider({
            min: this.min,
            max: this.max,

            change: function (event, ui) {
                if (this.value === ui.value) {
                    return;
                }
                this.value = ui.value;
                const slideChangeEvent = new CustomEvent(
                    'custom-slide-changed',
                    {detail: {value: ui.value}}
                );
                this.dispatchEvent(slideChangeEvent);
            }
        });
    }

    /**
     * @protected
     */
    _onValueChange(value) {
        if (this._slider === undefined) {
            return;
        }

        this._slider.slider("value", value);
    }

    /**
     * @protected
     */
    _onMinChange(value) {
        if (this._slider === undefined) {
            return;
        }

        this._slider.slider("option", "min", value);
    }

    /**
     * @protected
     */
    _onMaxChange(value) {
        if (this._slider === undefined) {
            return;
        }

        this._slider.slider("option", "max", value);
    }
}

defineCustomElement(Slider);
