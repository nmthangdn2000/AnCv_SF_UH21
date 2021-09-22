import 'package:intl/intl.dart';

class FormatDate {
  formatDate(mls) {
    final DateFormat formatter = DateFormat('HH:mm dd/MM/yyyy');
    return formatter
        .format(new DateTime.fromMicrosecondsSinceEpoch(mls * 1000));
  }
}
