import sys
with open(sys.argv[1], "r") as f:
    main_list = []
    for i in f:
        i = i.strip("\n")
        i = i.strip(" ")
        i = i.split(" ")
        main_list.append(i)
score = 0
point_list = []
e = 1
direct = 0
p = True
for i in main_list:
    print(*i)
print()
print("Your score is: 0")
print()


def bum(y, x):
    global direct
    global point_list
    global e
    global score
    if holynum != "X":
        if x != 0:
            if main_list[y][x - 1] == holynum and direct != 1:
                if e == 1:
                    point_list.append(holynum)
                    main_list[y].pop(x)
                    main_list[y].insert(x, " ")
                    e = 0
                point_list.append(holynum)
                main_list[y].pop(x - 1)
                main_list[y].insert(x - 1, " ")
                direct = 3
                bum(y, x - 1)
        if x != len(main_list[y]) - 1:
            if main_list[y][x + 1] == holynum and direct != 3:
                if e == 1:
                    point_list.append(holynum)
                    main_list[y].pop(x)
                    main_list[y].insert(x, " ")
                    e = 0
                point_list.append(holynum)
                main_list[y].pop(x + 1)
                main_list[y].insert(x + 1, " ")
                direct = 1
                bum(y, x + 1)
        if y != 0:
            if main_list[y - 1][x] == holynum and direct != 4:
                if e == 1:
                    point_list.append(holynum)
                    main_list[y].pop(x)
                    main_list[y].insert(x, " ")
                    e = 0
                point_list.append(holynum)
                main_list[y - 1].pop(x)
                main_list[y - 1].insert(x, " ")
                direct = 2
                bum(y - 1, x)
        if y != len(main_list) - 1:
            if main_list[y + 1][x] == holynum and direct != 2:
                if e == 1:
                    point_list.append(holynum)
                    main_list[y].pop(x)
                    main_list[y].insert(x, " ")
                    e = 0
                point_list.append(holynum)
                main_list[y + 1].pop(x)
                main_list[y + 1].insert(x, " ")
                direct = 4
                bum(y + 1, x)
    bomb(y, x)
    check()
    slide()
    del_column()
    del_line()
    print()
    for i in main_list:
        print(*i)
    sco()
    game_over()
    new()


def sco():
    global score
    for i in point_list:
        if i == "B":
            score += 9
        elif i == "G":
            score += 8
        elif i == "W":
            score += 7
        elif i == "Y":
            score += 6
        elif i == "R":
            score += 5
        elif i == "P":
            score += 4
        elif i == "O":
            score += 3
        elif i == "D":
            score += 2
        elif i == "F":
            score += 1
    print()
    print("Your score is: {}".format(score))
    print()


def new():
    global main_list
    global point_list
    global e
    global holynum
    global direct
    global p
    p = True
    direct = 0
    point_list = []
    try:
        y, x = input("Please enter a row and column number: ").split(" ")
        x = int(x)
        y = int(y)
        holynum = main_list[y][x]
        assert holynum != " "
    except:
        print()
        print("Please enter a valid size!")
        print()
        new()
    e = 1
    bum(y, x)


def slide():
    for a in range(len(main_list)):
        for b in range(len(main_list[0])):
            for c in range(len(main_list)):
                if c - 1 >= 0 and main_list[c][b] == " " and main_list[c - 1][b] != " ":
                    main_list[c][b] = main_list[c - 1][b]
                    main_list[c - 1][b] = " "


def check():
    if holynum != "X":
        for a in range(len(main_list)):
            for b in range(len(main_list[0])):
                for c in range(len(main_list)):
                    if c - 1 >= 0 and main_list[c][b] == " " and main_list[c - 1][b] == holynum:
                        point_list.append(holynum)
                        main_list[c - 1].pop(b)
                        main_list[c - 1].insert(b, " ")
                    if c + 1 <= len(main_list) - 1 and main_list[c][b] == " " and main_list[c + 1][b] == holynum:
                        point_list.append(holynum)
                        main_list[c + 1].pop(b)
                        main_list[c + 1].insert(b, " ")
                    if b - 1 >= 0 and main_list[c][b] == " " and main_list[c][b - 1] == holynum:
                        point_list.append(holynum)
                        main_list[c].pop(b - 1)
                        main_list[c].insert(b - 1, " ")
                    if b + 1 <= len(main_list[c]) - 1 and main_list[c][b] == " " and main_list[c][b + 1] == holynum:
                        point_list.append(holynum)
                        main_list[c].pop(b + 1)
                        main_list[c].insert(b + 1, " ")


def del_line():
    count = 0
    for a in range(len(main_list)):
        try:
            for b in main_list[a]:
                if b == " ":
                    count += 1
                else:
                    pass
        except:
            continue
        if count == len(main_list[a]):
            main_list.pop(a)
        count = 0


def del_column():
    count = 0
    for a in range(len(main_list[-1])):
        for b in range(len(main_list)):
            try:
                if main_list[b][a] == " ":
                    count += 1
                else:
                    pass
            except:
                pass
        if count == len(main_list):
            try:
                for c in range(len(main_list)):
                    main_list[c][a] = main_list[c][a + 1]
                    main_list[c].pop(a + 1)
            except:
                for c in range(len(main_list)):
                    main_list[c].pop(a)
        count = 0


def bomb(y, x):
    if main_list[y][x] == "X":
        main_list[y][x] = " "
        for i in range(len(main_list)):
            if main_list[i][x] == "X":
                bomb(i, x)
            point_list.append(main_list[i][x])
            main_list[i][x] = " "
        for i in range(len(main_list[y])):
            if main_list[y][i] == "X":
                bomb(y, i)
            point_list.append(main_list[y][i])
            main_list[y][i] = " "


def game_over():
    p = 0
    for a in range(len(main_list)):
        for b in range(len(main_list[a])):
            if main_list[a][b] != " ":
                if b != 0:
                    if main_list[a][b - 1] == main_list[a][b]:
                        p = 1
                if b != len(main_list[a]) - 1:
                    if main_list[a][b + 1] == main_list[a][b]:
                        p = 1
                if a != 0:
                    if main_list[a - 1][b] == main_list[a][b]:
                        p = 1
                if a != len(main_list) - 1:
                    if main_list[a + 1][b] == main_list[a][b]:
                        p = 1
                if main_list[a][b] == "X":
                    p = 1
    if p == 0:
        print("Game Over!")
        quit()


new()
