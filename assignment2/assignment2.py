print("<-----RULES----->")
print("1. BRUSH DOWN")
print("2. BRUSH UP")
print("3. VEHICLE ROTATES RIGHT")
print("4. VEHICLE ROTATES LEFT")
print("5. MOVE UP TO X")
print("6. JUMP")
print("7. REVERSE DIRECTION")
print("8. VIEW THE MATRIX")
print("0. EXIT")
print("Please enter the commands with a plus sign (+) between them.")

x_co = 0
y_co = 0
brush_pos = 0
car_direction = 1

commands = input()
commands = commands.split("+")
matrix_length = int(commands[0])
commands.pop(0)
N = matrix_length
matrix = [[" " for z in range(N)] for z in range(N)]


def brush(x):
    global matrix
    if x == 1:
        brush_pos = 0
    elif x == 2:
        brush_pos = 1
        matrix[y_co][x_co] = "*"
    return brush_pos


def rotate(x):
    global car_direction
    if x == 3:
        car_direction += 1
        if car_direction == 5:
            car_direction = 1
    elif x == 4:
        car_direction -= 1
        if car_direction == -3:
            car_direction = 1
        elif car_direction == 0:
            car_direction = 4
        elif car_direction == -1:
            car_direction = 3
        elif car_direction == -2:
            car_direction = 2


def reverse():
    global car_direction
    if car_direction == 1:
        car_direction = 3
    elif car_direction == 2:
        car_direction = 4
    elif car_direction == 3:
        car_direction = 1
    elif car_direction == 4:
        car_direction = 2


def move(x_co, y_co, brush_pos, car_direction, moving):
    for i in range(0, moving):
        if car_direction == 1:
            if x_co < int(N-1):
                x_co += 1
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
            else:
                x_co = 0
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
        if car_direction == 3:
            if x_co > 0:
                x_co -= 1
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
            else:
                x_co = N-1
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
        if car_direction == 2:
            if y_co < int(N-1):
                y_co += 1
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
            else:
                y_co = 0
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
        if car_direction == 4:
            if y_co > 0:
                y_co -= 1
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
            else:
                y_co = N-1
                if brush_pos == 1:
                    matrix[y_co][x_co] = "*"
    return y_co,x_co,matrix


for i in range(0, len(commands)):
    if commands[i] == "1":
        brush_pos = brush(2)
    elif commands[i] == "2":
        brush_pos = brush(1)
    elif commands[i] == "3":
        rotate(3)
    elif commands[i] == "4":
        rotate(4)
    elif commands[i].find("_") == 1:
        moving = int(commands[i][2:])
        y_co,x_co,matrix = move(x_co, y_co, brush_pos, car_direction, moving)
    elif commands[i] == "6":
        brush_pos = 0
        y_co,x_co,matrix = move(x_co, y_co, 0, car_direction, 3)
    elif commands[i] == "7":
        reverse()
    elif commands[i] == "8":
        print("+" * (N+2))
        for t in matrix:
            print("+", *t, "+", sep="")
        print("+" * (N+2))
    elif commands[i] == "0":
        break
