# mergulho.py
import sys

# Ler a entrada do stdin
input_data = sys.stdin.read().strip().split('\n')

# Primeira linha da entrada
N, R = map(int, input_data[0].split())
returned = list(map(int, input_data[1].split()))

# Determinar os voluntários que não retornaram
all_volunteers = set(range(1, N + 1))
returned_set = set(returned)
not_returned = sorted(all_volunteers - returned_set)

# Produzir a saída conforme especificado
if not_returned:
    print(' '.join(map(str, not_returned)) + ' ')
else:
    print('*')
