The sample shows a wizard, a dialog that guides the user through data entry step by step. The wizard creates an `Employee` entity (see `Employee.java`) and has three steps:

- basic information about the employee;
- address;
- confirmation of the entered data.

Each step is a `fragment`. The fragments declare the data containers of `WizardDialog` with `provided="true"` and bind their components to them in XML, so all steps work with the same `Employee` instance. The fields on the first two steps are validated before the wizard moves to the next step. The **Back** and **Next** buttons switch steps, and a click on a finished step returns to it.

The wizard is based on the `tabSheet` component, and each step is a tab. `WizardDialog` enables the tabs of the current and finished steps and disables the next ones. Based on this state, `wizard-dialog.css` turns the tabs into a step indicator: the selected tab is the current step, other enabled tabs are finished steps. The styles hide the tab background, connect the steps with lines, and highlight the finished and current steps with the accent color.

Useful links:

- [Composite Component]({contextPath}/sample/composite-component): the `AddressComponent` used on the second step;
- [Fragments]({contextPath}/sample/fragments): fragments that implement the steps.
