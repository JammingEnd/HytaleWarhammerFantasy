#!/usr/bin/env python3
"""
Scans all JSON files under src/main/resources/FactionTypes, extracts all unique keys
found inside any "Traits" object, and writes them (sorted) to trait_types.txt at the
project root. Prints a short summary.

Usage: python3 scripts/extract_trait_types.py
"""
import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
FACTION_DIR = ROOT / "src" / "main" / "resources" / "FactionTypes"
OUT_FILE = ROOT / "trait_types.txt"

if not FACTION_DIR.exists():
    print(f"Error: folder not found: {FACTION_DIR}", file=sys.stderr)
    sys.exit(2)

trait_keys = set()
files_scanned = 0
errors = []


def collect_traits(obj):
    """Recursively scan obj for keys named 'Traits' whose value is a dict and
    collect the dict's keys into trait_keys."""
    if isinstance(obj, dict):
        for k, v in obj.items():
            if k == "Traits" and isinstance(v, dict):
                for tk in v.keys():
                    trait_keys.add(tk)
            else:
                collect_traits(v)
    elif isinstance(obj, list):
        for item in obj:
            collect_traits(item)


for path in sorted(FACTION_DIR.rglob("*.json")):
    files_scanned += 1
    try:
        text = path.read_text(encoding="utf-8")
        data = json.loads(text)
        collect_traits(data)
    except Exception as e:
        errors.append((str(path), str(e)))

# write output
with OUT_FILE.open("w", encoding="utf-8") as f:
    for key in sorted(trait_keys):
        f.write(key + "\n")

# summary
print(f"Scanned {files_scanned} JSON file(s) in {FACTION_DIR}")
print(f"Found {len(trait_keys)} unique trait key(s). Written to {OUT_FILE}")
if errors:
    print("Errors encountered while parsing files:")
    for p, e in errors:
        print(f" - {p}: {e}")

if len(trait_keys) == 0:
    print("(No trait keys found)")
else:
    # print a short preview
    preview = sorted(trait_keys)[:50]
    print("Preview:")
    for k in preview:
        print("  ", k)
