# zerinho_ou_um.py
import sys

# Ler a entrada do stdin
input_data = sys.stdin.read().strip()

# Dividir a entrada em três inteiros
A, B, C = map(int, input_data.split())

# Determinar o vencedor
if A == B == C:
    print('*')
elif A != B and A != C:
    print('A')
elif B != A and B != C:
    print('B')
else:
    print('C')
