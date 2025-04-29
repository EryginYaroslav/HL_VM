import argparse
import random
from datetime import date, timedelta
import requests
from faker import Faker

# Базовый URL вашего Spring‑Boot приложения
BASE_URL = "http://localhost:8080/api"
fake = Faker("ru_RU")

# Наборы для генерации тренировок
TRAINING_TYPES = ["бег", "йога", "велосипед", "силовая", "пилатес"]
DIFFICULTIES   = ["легкая", "средняя", "сложная"]

def clear_all():
    resp = requests.delete(f"{BASE_URL}/admin/clear")
    resp.raise_for_status()

def generate_client():
    full_name = fake.name()
    age = random.randint(18, 70)
    # Дата окончания абонемента в диапазоне ±60 дней от сегодня
    subscription_end = date.today() + timedelta(days=random.randint(-60, 60))
    return {
        "fullName": full_name,
        "age": age,
        "subscriptionEndDate": subscription_end.isoformat()
    }

def generate_training():
    ttype = random.choice(TRAINING_TYPES)
    duration = random.randint(30, 120)  # минут
    diff = random.choice(DIFFICULTIES)
    return {
        "type": ttype,
        "duration": duration,
        "difficulty": diff
    }

def estimate_calories(duration, difficulty):
    base = {"легкая": 5, "средняя": 8, "сложная": 10}[difficulty]
    return int(duration * (base + random.uniform(-1, 1)))

def generate_visit(client_ids, training_objs):

    client_id = random.choice(client_ids)
    training = random.choice(training_objs)
    # Дата визита — случайно за последние 30 дней
    days_ago = random.randint(0, 30)
    visit_date = date.today() - timedelta(days=days_ago)
    calories = estimate_calories(training["duration"], training["difficulty"])
    return {
        "client":   {"id": client_id},
        "training": {"id": training["id"]},
        "visitDate": visit_date.isoformat(),
        "caloriesBurned": calories
    }

def main(count_clients, count_trainings, count_visits, clear):
    # 1) Очистка по флагу
    if clear:
        clear_all()
        print("База очищена.")
    else:
        print("Очистка базы пропущена, данные будут добавлены к существующим.")

    # 2) Создаём клиентов
    client_ids = []
    for _ in range(count_clients):
        data = generate_client()
        r = requests.post(f"{BASE_URL}/client", json=data)
        r.raise_for_status()
        client_ids.append(r.json()["id"])
    print(f"Создано клиентов: {len(client_ids)}")

    # 3) Создаём тренировки
    training_objs = []
    for _ in range(count_trainings):
        data = generate_training()
        r = requests.post(f"{BASE_URL}/training", json=data)
        r.raise_for_status()
        obj = r.json()
        training_objs.append({
            "id": obj["id"],
            "duration": data["duration"],
            "difficulty": data["difficulty"]
        })
    print(f"Создано тренировок: {len(training_objs)}")

    # 4) Создаём посещения
    for _ in range(count_visits):
        visit_payload = generate_visit(client_ids, training_objs)
        r = requests.post(f"{BASE_URL}/visit", json=visit_payload)
        r.raise_for_status()
    print(f"Создано посещений: {count_visits}")

#    # 5) Получаем и выводим рейтинг за последний месяц
#    print("\nРейтинг активности за последний месяц:")
#    r = requests.get(f"{BASE_URL}/rating")
#    r.raise_for_status()
#    rating = r.json()
#    for entry in rating:
#        name = entry["client"]["fullName"]
#        cal  = entry["caloriesBurned"]
#        print(f"{name}: {cal} ккал")

if __name__ == "__main__":
    parser = argparse.ArgumentParser(
        description="Генерация тестовых данных для фитнес‑трекера"
    )
    parser.add_argument(
        "--clients", type=int, default=50,
        help="Сколько клиентов создать"
    )
    parser.add_argument(
        "--trainings", type=int, default=10,
        help="Сколько тренировок создать"
    )
    parser.add_argument(
        "--visits", type=int, default=200,
        help="Сколько посещений создать"
    )
    parser.add_argument(
        "--clear", action="store_true", default=False,
        help="Если указан — перед генерацией очищать базу (DELETE /api/clear)"
    )
    args = parser.parse_args()

    main(
        args.clients,
        args.trainings,
        args.visits,
        clear=args.clear
    )
