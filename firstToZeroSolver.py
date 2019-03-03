memoized = []

# 0 = LOSING
# 1 = WINNING
# 2 = TIE

START_VALUE = 50

def game_over(position):
	return True if position == 0 else False

def generate_moves(position):
	if position > 1:
		return (1, 2)
	else:
		return (1, )

def game_over_value(position):
	if game_over(position):
		return 0
	elif position < len(memoized):
		return memoized[position]
	else:
		position_value = 0
		for i in generate_moves(position):
			if game_over_value(position - i) == 0:
				position_value = 1
				break
		memoized.append(position_value)
		return position_value

def solve(position):
	if game_over_value(position) == 0:
		# lose slowly
		return 1
	else:
		for i in generate_moves(position):
			if game_over_value(position - i) == 0:
				return i

for i in range(START_VALUE + 1):
	val = "Win" if game_over_value(i) == 1 else "Lose"
	print(str(i) + ": " + val)
