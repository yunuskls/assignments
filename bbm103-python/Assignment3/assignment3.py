import sys

with open(sys.argv[1], "r") as f:
    x = []
    y = []
    for i in f:
        x.append(i.rstrip())
d = {}
for i in x:
    y.append(i.split(":"))
for j in y:
    d[j[0]] = j[1]
for i in d:
    x = d[i].split()
    d[i] = x

with open("output.txt", "w") as k:


    def ANU(new_user):
        global d
        if new_user in d:
            print("ERROR: Wrong input type! for 'ANU’! -- This user already exists!!", file=k)
        else:
            d[new_user] = []
            print("User '{}' has been added to the social network successfully".format(new_user), file=k)



    def DEU(delete_user):
        global d
        if delete_user in d:
            d.pop(delete_user)
            print("User '{}' and his/her all relations have been deleted successfully".format(delete_user), file=k)
            for i in d:
                if delete_user in d[i]:
                    d[i].remove(delete_user)
                else:
                    pass
        else:
            print("ERROR: Wrong input type! for 'DEU'!--There is no user named '{}'!!".format(delete_user), file=k)



    def ANF(add_friend, add_to):
        global d
        if add_friend in d and add_to not in d:
            print("ERROR: Wrong input type! for 'ANF'! -- No user named '{}' found!!".format(add_to), file=k)
        elif add_to in d and add_friend not in d:
            print("ERROR: Wrong input type! for 'ANF'! -- No user named '{}' found!!".format(add_friend), file=k)
        elif add_to not in d and add_friend not in d:
            print("Wrong input type! for 'ANF'! -- No user named '{}' and '{}' found!".format(add_friend, add_to), file=k)
        elif add_friend in d[add_to]:
            print("ERROR: A relation between '{}' and '{}' already exists!!".format(add_friend, add_to), file=k)
        elif add_friend in d and add_to in d:
            d[add_friend].append(add_to)
            d[add_to].append(add_friend)
            print("Relation between '{}' and '{}' has been added successfully".format(add_friend, add_to), file=k)





    def DEF(source_user, target_user):
        global d
        if source_user not in d and target_user not in d:
            print("ERROR: Wrong input type! for 'DEF'! -- No user named '{}' and '{}' found!".format(source_user, target_user), file=k)
        elif source_user in d and target_user not in d:
            print("ERROR: Wrong input type! for 'DEF'! -- No user named '{}' found!".format(target_user), file=k)
        elif source_user not in d and target_user in d:
            print("ERROR: Wrong input type! for 'DEF'! -- No user named '{}' found!".format(source_user), file=k)
        elif source_user not in d[target_user]:
            print("ERROR: No relation between '{}' and '{}' found!!".format(source_user, target_user), file=k)
        else:
            d[source_user].remove(target_user)
            d[target_user].remove(source_user)
            print("Relation between '{}' and '{}' has been deleted successfully".format(source_user, target_user), file=k)


    def CF(username):
        if username in d:
            cf = len(d[username])
            print("User '{}' has {} friends".format(username, cf), file=k)
        else:
            print("ERROR: Wrong input type! for 'CF'! -- No user named '{}' found!".format(username), file=k)


    def FPF(username, distance):
        if username not in d:
            print("ERROR: Wrong input type! for 'FPF'! -- No user named '{}' found!".format(username), file=k)
        else:
            t = []
            if distance == 1:
                set1 = d[username]
                print("User '{}' has {} possible friends when maximum distance is 1".format(username, int(len(d[username]))), file=k)
                print("These possible friends: {}".format(sorted(set1)), file=k)
            elif distance == 2:
                for i in d[username]:
                    t.extend(d[i])
                    t.extend(d[username])
                s = set(t)
                s.remove(username)
                print("User '{}' has {} possible friends when maximum distance is 2".format(username, int(len(s))), file=k)
                print("These possible friends: {}".format(sorted(s)), file=k)
            elif distance == 3:
                for i in d[username]:
                    t.extend(d[i])
                    t.extend(d[username])
                    for j in d[i]:
                        t.extend(d[j])
                s = set(t)
                s.remove(username)
                print("User '{}' has {} possible friends when maximum distance is 3".format(username, int(len(s))), file=k)
                print("These possible friends: {}".format(sorted(s)), file=k)

    def SF(username, MD):
        if username not in d:
            print("Error: Wrong input type! for 'SF'! -- No user named '{}' found!!".format(username), file=k)
        else:
            friends = []
            mut2 = []
            mut3 = []
            for i in d[username]:
                friends.extend(d[i])
            friends.sort()
            for i in friends:
                if friends.count(i) == 2:
                    mut2.append(i)
                elif friends.count(i) == 3:
                    mut3.append(i)
            set2 = set(mut2)
            set2.discard(username)
            set3 = set(mut3)
            set3.discard(username)
            combined = mut2 + mut3
            set_combined = set(combined)
            set_combined.discard(username)
            if MD == 2:
                print("Suggestion list for '{}' (when MD is 2)".format(username), file=k)
                print("'{}' has 2 mutual friends with '{}'".format(username, str(sorted(set2)[0])), file=k)
                print("'{}' has 3 mutual friends with '{}'".format(username, str(sorted(set2)[1])), file=k)
                print("The suggested friends for '{}': '{}'".format(username, set_combined), file=k)
            if MD == 3:
                print("Suggestion list for '{}' (when MD is 3)".format(username), file=k)
                print("'{}' has 3 mutual friends with '{}'".format(username, sorted(set3)), file=k)
                print("The suggested friends for '{}': '{}'".format(username, sorted(set3)), file=k)


    with open(sys.argv[2], "r") as commands:
        x = []
        y = []
        for i in commands:
            i = i.strip("\n")
            i = i.strip(" ")
            i = i.split(" ")
            if i[0] == "ANU":
                ANU(i[1])
            elif i[0] == "DEU":
                DEU(i[1])
            elif i[0] == "ANF":
                ANF(i[1], i[2])
            elif i[0] == "DEF":
                DEF(i[1],i[2])
            elif i[0] == "CF":
                CF(i[1])
            elif i[0] == "FPF":
                FPF(i[1], int(i[2]))
            elif i[0] == "SF":
                SF(i[1], int(i[2]))
