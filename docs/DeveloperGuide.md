---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# HallLedger Developer Guide

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Acknowledgements

* HallLedger is based on the **AddressBook-Level3 (AB3)** architecture and codebase from [se-education/addressbook-level3](https://github.com/se-edu/addressbook-level3).
* HallLedger’s demerit-rule feature is based on the idea of a hall/housing demerit system and was adapted to fit the resident-management workflow of this product.

--------------------------------------------------------------------------------------------------------------------

## Setting up, getting started

Refer to [SettingUp.md](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## Design

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="300" />

The **Architecture Diagram** above gives a high-level overview of HallLedger.

HallLedger follows the standard AB3-style layered architecture and consists of the following main components:

* **`UI`**: Handles the visible user interface.
* **`Logic`**: Parses commands and executes them.
* **`Model`**: Stores the in-memory application state.
* **`Storage`**: Saves and loads data from disk.

`Commons` contains utility classes shared across components.

Each major component:
* exposes an interface,
* has a concrete implementation,
* and interacts with other components through those interfaces.

This keeps the system modular and easier to extend.

---

### UI component

**API**: `Ui.java`

<puml src="diagrams/UiClassDiagram.puml" width="900" />

The UI consists of a `MainWindow` and a set of smaller `UiPart`s.

Important UI elements include:
* `CommandBox`
* `ResultDisplay`
* `ListSection`
* `TabSection`
* `StatusBarFooter`

The `TabSection` is especially important in HallLedger because it separates different views of resident information, such as:
* resident profile details,
* demerit records,
* and dashboard-oriented summaries.

The UI:
* sends user commands to the `Logic` component,
* listens to observable model state,
* and updates the display whenever the selected resident or filtered resident list changes.

---

### Logic component

**API**: `Logic.java`

<puml src="diagrams/LogicClassDiagram.puml" width="550" />

The `Logic` component is responsible for:
1. receiving raw user input,
2. identifying the command word,
3. delegating parsing to the appropriate parser,
4. constructing the corresponding command object,
5. executing the command,
6. and returning a `CommandResult`.

Examples of HallLedger-specific commands include:
* `TagCommand`
* `RemarkCommand`
* `DemeritCommand`
* `DemeritListCommand`

At a high level:
* `AddressBookParser` dispatches to a feature-specific parser such as `DemeritCommandParser`,
* the parser validates input and builds a command object,
* the command executes against the model,
* the result is returned to the UI.

---

### Model component

**API**: `Model.java`

<puml src="diagrams/ModelClassDiagram.puml" width="500" />

The `Model` component stores:
* the resident data,
* the currently filtered resident list,
* the currently selected resident,
* and user preferences.

HallLedger’s central domain entity is `Person`, which stores:
* core resident information,
* typed tags (year, major, gender),
* a resident-level remark,
* and demerit incidents.

This design keeps resident-related data grouped under one cohesive domain object, which fits the product scope of hall-level resident management.

---

### Storage component

**API**: `Storage.java`

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component reads and writes HallLedger data in JSON form.

Its responsibilities include:
* loading resident data at startup,
* saving resident data after mutating commands,
* and converting between model objects and Jackson-friendly adapted objects.

This is particularly important for HallLedger because storage must preserve not only resident contact details, but also:
* typed tags,
* remarks,
* and demerit incident histories.

---

### Common classes

Shared utility classes are placed in `seedu.address.commons`.

Examples include:
* logging utilities,
* configuration helpers,
* and general-purpose support classes used across multiple components.

--------------------------------------------------------------------------------------------------------------------

## Implementation

This section describes notable implementation details of HallLedger-specific features.

### Resident tagging

HallLedger allows each resident to store up to one value for each supported tag category:
* **Year**
* **Major**
* **Gender**

This is implemented using typed tags rather than a single flat unstructured tag set.

#### Why this design was chosen

A generic free-form tag list is flexible, but it also makes it harder to enforce consistency. For HallLedger, the target user often needs to search or group residents by a small number of predictable hall-relevant categories. Typed tags provide:
* more structured data,
* simpler validation,
* and clearer behavior for commands such as `find` and `tag`.

#### Command behavior

The `tag` command:
1. identifies the target resident using student ID,
2. parses any provided tag values,
3. updates only the specified tag categories,
4. and leaves the other categories unchanged.

This design avoids forcing the user to re-enter all tag values whenever they want to update just one of them.

---

### Resident remarks

HallLedger supports a resident-level remark through the `remark` command.

A remark is designed for short operational notes, for example:
* allergies,
* late arrival notes,
* welfare-related reminders,
* or other administration-relevant context.

#### Why this design was chosen

Remarks are intentionally lightweight:
* they are resident-level, not tied to a separate note entity,
* they are quick to update,
* and they fit the hall-admin use case better than a more complex note-management subsystem.

This keeps HallLedger focused and avoids feature bloat.

---

### Demerit point tracking

HallLedger stores demerits as **individual incident records** attached to a resident.

Each demerit incident stores:
* the rule index,
* the rule title,
* the offence number for that resident and rule,
* the points applied for that occurrence,
* and an optional incident remark.

The resident’s total demerit points are derived from the incident list rather than stored as duplicated mutable state.

#### Why this design was chosen

A single running total would lose important context:
* which rule was applied,
* how many prior offences existed for that rule,
* and why the current number of points was assigned.

By storing individual incidents, HallLedger can:
* preserve an auditable resident history,
* calculate the total points when needed,
* and track repeated offences correctly per rule.

---

### Demerit application flow

The `demerit` command is implemented through `DemeritCommandParser` and `DemeritCommand`.

At a high level, the flow is:

1. The parser validates the student ID and rule index.
2. The command locates the target resident.
3. The command retrieves the demerit rule from the rule catalogue.
4. The command counts prior occurrences of that rule for the resident.
5. The command computes the offence number and applied points.
6. A new `DemeritIncident` is created.
7. The updated incident list is attached to a new `Person`.
8. The model replaces the old resident with the updated resident.
9. The UI refreshes and the success feedback is shown.

This design keeps the demerit logic explicit and easy to test.

#### Notable user-facing behavior

The demerit success message includes:
* the target resident,
* the rule applied,
* the incident remark,
* the points added,
* and the resident’s updated total demerit points.

This gives the user immediate confirmation that the intended incident was recorded.

---

### Demerit records UI

HallLedger provides a dedicated **Demerit Records** tab for the selected resident.

The tab displays:
* total accumulated demerit points,
* the resident’s incident history,
* the rule description,
* incident remarks,
* and the points applied for each entry.

The demerit table is tied to the currently selected resident.  
When the selected resident changes, the tab content updates accordingly.

This design keeps demerit information visible without cluttering the main resident list.

--------------------------------------------------------------------------------------------------------------------

## Appendix: Requirements

### Product scope

**Target user profile**

HallLedger is for:
* Resident Assistants (RAs),
* hall administrators,
* or student leaders handling resident administration in a hall setting.

The target user:
* manages many residents,
* frequently needs to retrieve and update resident data,
* prefers keyboard-driven workflows where possible,
* and values speed, structure, and local data control.

**Value proposition**

HallLedger helps hall administrators manage resident information, remarks, tags, and demerit incidents more efficiently than scattered spreadsheets or note-taking workflows.

---

### User stories

Priorities: High (`***`), Medium (`**`), Low (`*`)

| Priority | As a ... | I want to ... | So that I can ... |
| --- | --- | --- | --- |
| `***` | hall administrator | add resident records | keep the resident list up to date |
| `***` | hall administrator | edit resident records | correct outdated or wrong information |
| `***` | hall administrator | delete resident records | remove residents who are no longer relevant |
| `***` | hall administrator | find residents using key fields | locate residents quickly |
| `***` | hall administrator | tag residents by year, major, and gender | organize residents in hall-relevant ways |
| `***` | hall administrator | add remarks to residents | store important short operational notes |
| `***` | hall administrator | add demerit incidents to residents | keep track of rule breaches |
| `**` | hall administrator | view a resident’s demerit history | understand how their current total was formed |
| `**` | hall administrator | see the demerit rule list | apply the correct rule consistently |
| `*` | hall administrator | archive older data later | keep the working dataset cleaner |

---

### Use cases

#### UC1: Add a resident

**MSS**
1. User enters a valid `add` command with the required resident details.
2. HallLedger validates the input.
3. HallLedger adds the resident.
4. HallLedger shows a success message.

Use case ends.

**Extensions**
* 2a. Some input fields are invalid.
  * 2a1. HallLedger rejects the command and shows an error message.
  * Use case ends.
* 2b. The resident conflicts with an existing resident.
  * 2b1. HallLedger rejects the command and shows an error message.
  * Use case ends.

---

#### UC2: Edit a resident

**MSS**
1. User enters a valid `edit` command for an existing resident.
2. HallLedger validates the input.
3. HallLedger updates the resident.
4. HallLedger shows a success message.

Use case ends.

**Extensions**
* 1a. The target student ID does not exist.
  * 1a1. HallLedger shows an error message.
  * Use case ends.
* 2a. The edit contains invalid values.
  * 2a1. HallLedger rejects the command.
  * Use case ends.

---

#### UC3: Find residents

**MSS**
1. User enters a `find` command using one or more supported fields.
2. HallLedger filters the resident list.
3. HallLedger shows the filtered list.

Use case ends.

---

#### UC4: Tag a resident

**MSS**
1. User enters a valid `tag` command with one or more supported tag values.
2. HallLedger validates the command.
3. HallLedger updates the resident’s tags.
4. HallLedger shows a success message.

Use case ends.

---

#### UC5: Add or update a resident remark

**MSS**
1. User enters a valid `remark` command.
2. HallLedger validates the command.
3. HallLedger updates the resident’s remark.
4. HallLedger shows a success message.

Use case ends.

---

#### UC6: View demerit rules

**MSS**
1. User enters `demeritlist`.
2. HallLedger shows the available demerit rules.

Use case ends.

---

#### UC7: Add a demerit incident

**MSS**
1. User enters a valid `demerit` command with a resident ID and rule index.
2. HallLedger validates the input.
3. HallLedger determines the offence number for that resident and rule.
4. HallLedger computes the points applied for that occurrence.
5. HallLedger records the incident.
6. HallLedger shows a success message and updates the resident’s demerit records.

Use case ends.

**Extensions**
* 2a. The resident does not exist.
  * 2a1. HallLedger shows an error message.
  * Use case ends.
* 2b. The demerit rule index does not exist.
  * 2b1. HallLedger shows an error message.
  * Use case ends.

---

#### UC8: Delete a resident

**MSS**
1. User enters a valid `delete` command.
2. HallLedger opens a confirmation dialog.
3. User confirms the deletion.
4. HallLedger deletes the resident.
5. HallLedger shows a success message.

Use case ends.

**Extensions**
* 2a. User cancels the deletion.
  * 2a1. HallLedger aborts deletion and shows a cancellation message.
  * Use case ends.
* 1a. The command format is invalid.
  * 1a1. HallLedger shows an error message instead of opening the dialog.
  * Use case ends.

---

### Non-Functional Requirements

1. HallLedger should work on mainstream desktop operating systems that support Java 17.
2. HallLedger should remain usable for a typical hall-level resident dataset.
3. A fast typist should be able to complete common administrative tasks efficiently through commands.
4. HallLedger should store data locally in a human-editable file.
5. HallLedger should work without requiring internet access during normal usage.
6. HallLedger should remain a single-user application.

---

### Glossary

* **Resident**: A hall resident stored in HallLedger.
* **RA**: Resident Assistant.
* **Student ID**: The unique identifier used by HallLedger to target a resident.
* **Remark**: A short resident-level operational note.
* **Demerit incident**: A single recorded rule breach applied to a resident.
* **Demerit rule**: A rule in the demerit catalogue that can be applied using `demerit`.

--------------------------------------------------------------------------------------------------------------------

## Appendix: Instructions for Manual Testing

Given below are instructions to test the app manually.

<box type="info" seamless>

These instructions only provide a starting point. Testers are expected to do exploratory testing beyond them.

</box>

### Launch and shutdown

1. Initial launch
   1. Download the jar file into an empty folder.
   2. Run the app.  
      Expected: the GUI shows sample data.

2. Closing and reopening
   1. Close the app.
   2. Launch it again.  
      Expected: data and preferences are restored.

---

### Adding a resident

1. Valid add
   1. Enter  
      `add n=John Doe p=+6598765432 e=johnd@example.com i=A1234567X r=15R ec=+65 91234567`
   2. Verify that the resident is added and the result box shows success.

2. Invalid add
   1. Enter an add command with an invalid field value.
   2. Verify that HallLedger rejects the command.

---

### Finding residents

1. Enter  
   `find n=John`
2. Verify that the list is filtered appropriately.

3. Enter  
   `find y=Y2 m=Computer Science`
4. Verify that the list is filtered accordingly.

---

### Tagging a resident

1. Enter  
   `tag i=A1234567X y=Y2 m=Computer Science g=Male`
2. Verify that the target resident’s tags are updated.

---

### Adding a remark

1. Enter  
   `remark i=A1234567X rm=Peanut allergy`
2. Verify that the resident remark is updated.

---

### Demerit features

1. Show demerit rules
   1. Enter `demeritlist`
   2. Verify that HallLedger shows the supported rule list.

2. Add a demerit incident
   1. Enter  
      `demerit i=A1234567X di=18 rm=Visitor during quiet hours`
   2. Verify that:
      * the command succeeds,
      * the success message includes the remark,
      * the resident’s demerit tab updates,
      * and the total demerit points increase correctly.

3. Repeated offence
   1. Enter the same `demerit` command again.
   2. Verify that the offence number and points reflect a repeated offence for that rule.

4. Invalid rule
   1. Enter  
      `demerit i=A1234567X di=999`
   2. Verify that HallLedger rejects the command.

---

### Deleting a resident

1. Enter  
   `delete i=A1234567X`
2. Verify that a confirmation dialog appears.
3. Cancel the deletion.  
   Expected: resident remains.
4. Repeat and confirm the deletion.  
   Expected: resident is removed.

--------------------------------------------------------------------------------------------------------------------
