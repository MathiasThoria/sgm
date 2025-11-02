#!/bin/bash
# Combina todos los archivos .java en un solo markdown

OUTPUT="codigo_completo.md"
SOURCE_DIR="src"

echo "# Código fuente completo del proyecto" > "$OUTPUT"
echo "" >> "$OUTPUT"

find "$SOURCE_DIR" -type f -name "*.java" | sort | while read -r file; do
    echo "## Archivo: $file" >> "$OUTPUT"
    echo '```java' >> "$OUTPUT"
    cat "$file" >> "$OUTPUT"
    echo '```' >> "$OUTPUT"
    echo "" >> "$OUTPUT"
done

echo "✅ Archivo combinado generado en: $OUTPUT"
