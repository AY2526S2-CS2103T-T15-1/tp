---
layout: default.md
title: "Fuzzy Matching Details"
pageNav: 3
---

# Fuzzy Matching Details

This page explains how Hall Ledger decides whether a resident matches your `find` input.

## 1. Quick idea

<box type="info" seamless>

Hall Ledger uses fuzzy matching to make search more forgiving.

In simple terms, your keyword is treated as a match when:

1. It is the same text (ignoring uppercase/lowercase), or
2. It appears inside the target text, or
3. It is very close to the target text with a small typo (for longer words).

For short keywords (3 letters or less), Hall Ledger only uses rule 1: exact matching, and rule 2: substring
matching.
This prevents too many unrelated results.

Examples:

- `ALEX` matches `alex`.
- `ali` matches `Alice`. (`ali` is a substring of `Alice`.)
- `sitten` can still match `kitten` (small typo).
- `ann` does **not** match `ana` (the keywords are too short, so the typo is not forgiven).
</box>

Examples:

| You type | Resident value | Match? | Why |
| --- | --- | --- | --- |
| `ali` | `Alice` | Yes | `ali` is contained in `Alice`. |
| `ALEX` | `alex` | Yes | Matching ignores letter case. |
| `sitten` | `kitten` | Yes | Small typo, still considered close. |
| `ann` | `ana` | No | Not close enough for a short input. |

## 2. Which fields use which matching style?

| Prefix | Field             | Matching style             | Example                                                                                |
|--------|-------------------|----------------------------|----------------------------------------------------------------------------------------|
| `n=`   | Name              | Fuzzy                      | `n=alex` can match `Alex Tan`.                                                         |
| `p=`   | Phone             | Fuzzy                      | `p=9123` can match `+65 91234567`.                                                     |
| `e=`   | Email             | Fuzzy                      | `e=@gmail` can match `alex@gmail.com`.                                                 |
| `i=`   | Student ID        | Exact (case-insensitive)*  | `i=a1234567x` matches `A1234567X`; `i=1234` does **not** match `A1234567X`.            |
| `ec=`  | Emergency contact | Fuzzy                      | `ec=9876` can match `+65 98765432`.                                                    |
| `r=`   | Room number       | Fuzzy                      | `r=12` can match `12A`.                                                                |
| `y=`   | Year tag          | Fuzzy**                    | `y=1` matches a resident tagged with year `1`.                                         |
| `m=`   | Major tag         | Fuzzy                      | `m=computer sci` can match `Computer Science`.                                         |
| `g=`   | Gender tag        | Exact (case-insensitive)** | `g=he` matches `he/him`; `g=her` matches `she/her`; `g=they/them` matches `they/them`. |

## 3. Notes on exact-match fields

\* **Student ID** uses exact matching because it is a unique identifier.
Partial or fuzzy matches would produce too many false positives, since many student IDs share similar digit sequences.

<box type="tip" seamless>

**Tip:** Because each Student ID is unique, searching with the full ID (e.g. `find i=A1234567X`) is guaranteed to return
exactly one resident. This is a quick way to pull up a specific resident's profile.

</box>

\** **Year** and **Gender** keywords are **normalised before matching**.
See [Section 4](#4-note-on-year-and-gender-keywords) for details.

## 4. Note on Year and Gender keywords

Hall Ledger only accepts a fixed set of values for Year and Gender tags:

- **Valid year values:** `1`, `2`, `3`, `4`, `5`, `6`
- **Valid gender values:** `he/him`, `she/her`, `they/them`

This is why the Filter Panel uses selection boxes for these two fields. This data validation is meant to help you ensure
consistent tagging across residents.

![Year and Gender Combo Box](images/combo-boxes.png)

**How normalisation works:**

When you use `find` (or the Filter Panel), Hall Ledger normalises your input before matching:

- **Year** keywords must be an integer from `1`–`6` exactly. Any other value (e.g. `Y1`, `7`) is invalid.
- **Gender** keywords are flexible: shorthand forms like `he`, `him`, `she`, `her`, `they`, or `them` are automatically
  expanded to their full form (`he/him`, `she/her`, `they/them`) before matching. Any other value (e.g. `male`,
  `female`) is invalid.

If you enter an invalid year or gender keyword, Hall Ledger will **ignore it and show a warning** — the rest of your
search still runs normally.

![Warning - Ignored values](images/warning-ignored-values-for-year-and-gender.png)

## 5. How multiple filters combine

<box type="info" seamless>

- Using different prefixes means a resident must match **all** fields.
    - `find n=Alice y=1` means: name matches `Alice` **and** year matches `1`.
    - `find ec=alice@gmail.com m=CS` means: emergency contact matches `alice@gmail.com` **and** major matches `CS`.
- Repeating the same prefix widens that single filter.
    - `find y=2 y=3` means: year is `2` **or** `3`.
  - `find n=Alice n=Bob` means: name matches `Alice` **or** `Bob`.
    - `find n=Alice n=Bob y=2 y=3` means: (name matches `Alice` **or** `Bob`) **and** (year is `2` **or** `3`).

</box>

## 6. Case sensitivity

All `find` matching is case-insensitive.

- `find n=alice` and `find n=ALICE` behave the same.
- `find e=GMAIL` can match `gmail.com`.
