---
layout: default.md
title: "Fuzzy Matching Details"
pageNav: 3
---

# Fuzzy Matching Details

This page explains how Hall Ledger matches search keywords when you use `find`.

## 1. Name fuzzy matching (`find Alice` or `find n=Alice`)

Name matching compares each word in a resident's name against your input keywords.

For each keyword-to-name-word comparison, Hall Ledger applies these rules in order:

1. **Exact match (case-insensitive)**
2. **Substring match**: keyword is contained inside the name word
3. **Typo-tolerant match**: if both strings are at least 4 characters, we allow up to 2 small typing mistakes
   (for example: changing, adding, or removing up to 2 letters)

### Examples

- Keyword `alice` matches name words: `Alice`, `ALICE`, `malice`
- Keyword `alex` matches `alxe` (letters swapped by mistake)
- Keyword `alex` matches 'alrc' (2 typos: 'x' changed to 'r', 'e' changed to 'c')
- Keyword `abc` does **not** match `acd` (too short for typo-tolerant matching and not a substring)

## 2. Prefix-based `find` matching behavior

When using prefixed search, each field has its own matching strategy.

- `n=` (name): fuzzy matching rules above
- `p=` (phone): case-insensitive partial match
- `e=` (email): case-insensitive partial match
- `i=` (student ID): case-insensitive partial match
- `r=` (room number): case-insensitive exact match
- `ec=` (emergency contact): case-insensitive exact match
- `y=` (year tag): case-insensitive exact tag match
- `g=` (gender tag): case-insensitive exact tag match
- `m=` (major tag): case-insensitive partial tag match

## 3. AND/OR rules in prefixed `find`

- **Different prefixes** are combined with **AND**.
  - Example: `find n=Alice y=Y1` returns only residents matching both conditions.
- **Repeated same prefix** values are combined with **OR**.
  - Example: `find y=Y2 y=Y3` returns residents in Year 2 or Year 3.

## 4. Case sensitivity

All `find` matching is case-insensitive.

- `find n=alice` and `find n=ALICE` behave the same.
- `find e=GMAIL` matches `gmail.com`.

