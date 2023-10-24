from redbotton_crawler import get_game_data

data = get_game_data("redbotton.html")

print(data)

data.to_csv('game.csv', index=False, encoding='utf-8-sig')